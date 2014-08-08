/* ***** BEGIN LICENSE BLOCK *****
 * Distributed under the BSD license:
 *
 * Copyright (c) 2010, Ajax.org B.V.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Ajax.org B.V. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL AJAX.ORG B.V. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ***** END LICENSE BLOCK ***** */

ace.define('ace/mode/c9search', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/mode/text', 'ace/tokenizer', 'ace/mode/c9search_highlight_rules', 'ace/mode/matching_brace_outdent', 'ace/mode/folding/c9search'], function(require, exports, module) {


var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var Tokenizer = require("../tokenizer").Tokenizer;
var C9SearchHighlightRules = require("./c9search_highlight_rules").C9SearchHighlightRules;
var MatchingBraceOutdent = require("./matching_brace_outdent").MatchingBraceOutdent;
var C9StyleFoldMode = require("./folding/c9search").FoldMode;

var Mode = function() {
    this.HighlightRules = C9SearchHighlightRules;
    this.$outdent = new MatchingBraceOutdent();
    this.foldingRules = new C9StyleFoldMode();
};
oop.inherits(Mode, TextMode);

(function() {
    
    this.getNextLineIndent = function(state, line, tab) {
        var indent = this.$getIndent(line);
        return indent;
    };

    this.checkOutdent = function(state, line, input) {
        return this.$outdent.checkOutdent(line, input);
    };

    this.autoOutdent = function(state, doc, row) {
        this.$outdent.autoOutdent(doc, row);
    };

}).call(Mode.prototype);

exports.Mode = Mode;

});

ace.define('ace/mode/c9search_highlight_rules', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/lib/lang', 'ace/mode/text_highlight_rules'], function(require, exports, module) {


var oop = require("../lib/oop");
var lang = require("../lib/lang");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

function safeCreateRegexp(source, flag) {
    try {
        return new RegExp(source, flag);
    } catch(e) {}
}

var C9SearchHighlightRules = function() {
    this.$rules = {
        "start" : [
            {
                tokenNames : ["c9searchresults.constant.numeric", "c9searchresults.text", "c9searchresults.text", "c9searchresults.keyword"],
                regex : "(^\\s+[0-9]+)(:\\s)(.+)",
                onMatch : function(val, state, stack) {
                    var values = this.splitRegex.exec(val);
                    var types = this.tokenNames;
                    var tokens = [{
                        type: types[0],
                        value: values[1]
                    },{
                        type: types[1],
                        value: values[2]
                    }];
                    
                    var regex = stack[1];
                    var str = values[3];
                    if (regex && str) 
                        values = str.split(regex); // this doesn't work on ie8 but we don't care:)
                    else
                        values = [str];
                    
                    for (var i = 0, l = values.length; i < l; i+=2) {
                        if (values[i])
                            tokens.push({
                                type: types[2],
                                value: values[i]
                            });
                        if (values[i+1])
                            tokens.push({
                                type: types[3],
                                value: values[i + 1]
                            });
                    }
                    return tokens;
                }
            },
            {
                token : ["string", "text"], // single line
                regex : "(\\S.*)(:$)"
            },
            {
                regex : "Searching for .*$",
                onMatch: function(val, state, stack) {
                    var parts = val.split("\x01");
                    var search = parts[1];
                    if (parts.length < 3)
                        return "text";
                    var options = parts[2] == " in" ? parts[5] : parts[6];

                    if (!/regex/.test(options))
                        search = lang.escapeRegExp(search);
                    if (/whole/.test(options))
                        search = "\\b" + search + "\\b";
                    var regex = safeCreateRegexp(
                        "(" + search + ")",
                        / sensitive/.test(options) ? "" : "i"
                    );
                    if (regex) {
                        stack[0] = state;
                        stack[1] = regex;
                    }
                    
                    var i = 0;
                    var tokens = [
                        {
                            value: parts[i++] + "'",
                            type: "text"
                        },
                        {
                            value: parts[i++],
                            type: "text" // "c9searchresults.keyword"
                        },
                        {
                            value: "'" + parts[i++],
                            type: "text"
                        }
                    ];
                    if (parts[2] !== " in") {
                        tokens.push({
                            value: "'" + parts[i++] + "'",
                            type: "text"
                        }, {
                            value: parts[i++],
                            type: "text"
                        });
                    }
                    tokens.push({
                        value: " " + parts[i++] + " ",
                        type: "text"
                    });
                    if (parts[i+1]) {
                        tokens.push({
                            value: "(" + parts[i+1] + ")",
                            type: "text"
                        });
                        i += 1;
                    } else {
                        i -= 1;
                    }
                    while (i++ < parts.length)
                        parts[i] && tokens.push({
                            value: parts[i],
                            type: "text"
                        });
                    
                    return tokens;
                }
            },
            {
                regex : "\\d+",
                token: "constant.numeric"
            }
        ]
    };
};

oop.inherits(C9SearchHighlightRules, TextHighlightRules);

exports.C9SearchHighlightRules = C9SearchHighlightRules;

});

ace.define('ace/mode/matching_brace_outdent', ['require', 'exports', 'module' , 'ace/range'], function(require, exports, module) {


var Range = require("../range").Range;

var MatchingBraceOutdent = function() {};

(function() {

    this.checkOutdent = function(line, input) {
        if (! /^\s+$/.test(line))
            return false;

        return /^\s*\}/.test(input);
    };

    this.autoOutdent = function(doc, row) {
        var line = doc.getLine(row);
        var match = line.match(/^(\s*\})/);

        if (!match) return 0;

        var column = match[1].length;
        var openBracePos = doc.findMatchingBracket({row: row, column: column});

        if (!openBracePos || openBracePos.row == row) return 0;

        var indent = this.$getIndent(doc.getLine(openBracePos.row));
        doc.replace(new Range(row, 0, row, column-1), indent);
    };

    this.$getIndent = function(line) {
        return line.match(/^\s*/)[0];
    };

}).call(MatchingBraceOutdent.prototype);

exports.MatchingBraceOutdent = MatchingBraceOutdent;
});


ace.define('ace/mode/folding/c9search', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/range', 'ace/mode/folding/fold_mode'], function(require, exports, module) {


var oop = require("../../lib/oop");
var Range = require("../../range").Range;
var BaseFoldMode = require("./fold_mode").FoldMode;

var FoldMode = exports.FoldMode = function() {};
oop.inherits(FoldMode, BaseFoldMode);

(function() {

    this.foldingStartMarker = /^(\S.*\:|Searching for.*)$/;
    this.foldingStopMarker = /^(\s+|Found.*)$/;
    
    this.getFoldWidgetRange = function(session, foldStyle, row) {
        var lines = session.doc.getAllLines(row);
        var line = lines[row];
        var level1 = /^(Found.*|Searching for.*)$/;
        var level2 = /^(\S.*\:|\s*)$/;
        var re = level1.test(line) ? level1 : level2;

        if (this.foldingStartMarker.test(line)) {            
            for (var i = row + 1, l = session.getLength(); i < l; i++) {
                if (re.test(lines[i]))
                    break;
            }

            return new Range(row, line.length, i, 0);
        }

        if (this.foldingStopMarker.test(line)) {
            for (var i = row - 1; i >= 0; i--) {
                line = lines[i];
                if (re.test(line))
                    break;
            }

            return new Range(i, line.length, row, 0);
        }
    };
    
}).call(FoldMode.prototype);

});

