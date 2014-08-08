

var editor = ace.edit("editor");

editor.setTheme("ace/src/pastel_on_dark");

editor.getSession().setMode("ace/src/php");

document.getElementById('editor').style.fontSize = '20px';

editor.gotoLine(1);
