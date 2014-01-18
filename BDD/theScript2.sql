/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de cr�ation :  28/12/2013 10:48:50                      */
/*==============================================================*/


drop table if exists JOINTURE;

drop table if exists admin;

drop table if exists candidat;

drop table if exists categorie;

drop table if exists passage;

drop table if exists proposition;

drop table if exists question;

drop table if exists reponse;

drop table if exists reponse_histo;

drop table if exists rubrique;

drop table if exists test;

/*==============================================================*/
/* Table : JOINTURE                                             */
/*==============================================================*/
create table JOINTURE
(
   PASSAGEID            int not null,
   TESTID               int not null
);

/*==============================================================*/
/* Table : admin                                                */
/*==============================================================*/
create table admin
(
   ADMINID              int not null,
   ADMINLOGIN           varchar(255) default NULL,
   ADMINPASSWORD        varchar(255) default NULL,
   primary key (ADMINID)
);

/*==============================================================*/
/* Table : candidat                                             */
/*==============================================================*/
create table candidat
(
   CANDIDATID           int not null,
   CANDIDAT_CIVILITE    varchar(5) default NULL,
   CANDIDAT_NOM         varchar(255) default NULL,
   CANDIDAT_PRENOM      varchar(255) default NULL,
   CANDIDAT_MAIL        varchar(255) default NULL,
   CANDIDAT_DATE_NAISSANCE date default NULL,
   primary key (CANDIDATID)
);

/*==============================================================*/
/* Table : categorie                                            */
/*==============================================================*/
create table categorie
(
   CATEGORIEID          int not null,
   CATEGORIELIBELLE     text,
   primary key (CATEGORIEID)
);

/*==============================================================*/
/* Table : passage                                              */
/*==============================================================*/
create table passage
(
   PASSAGEID            int not null,
   CANDIDATID           int not null,
   PASSAGE_ETAT         int default NULL,
   PASSAGE_DATE         date default NULL,
   PASSAGEDEBUT_VAL     datetime default NULL,
   PASSAGEFIN_VAL       datetime default NULL,
   primary key (PASSAGEID)
);

/*==============================================================*/
/* Table : proposition                                          */
/*==============================================================*/
create table proposition
(
   PROPOSITIONID        int not null,
   QUESTIONID           int not null,
   PROPOSITIONLIBELLE   varchar(1000) default NULL,
   PROPOSITIONVRAI      tinyint default NULL,
   primary key (PROPOSITIONID)
);

/*==============================================================*/
/* Table : question                                             */
/*==============================================================*/
create table question
(
   QUESTIONID           int not null,
   RUBRIQUEID           int not null,
   QUESTIONTEXT         varchar(255) default NULL,
   primary key (QUESTIONID)
);

/*==============================================================*/
/* Table : reponse                                              */
/*==============================================================*/
create table reponse
(
   REPONSEID            int not null,
   PASSAGEID            int not null,
   PROPOSITIONID        int not null,
   REPONSETEXTE         varchar(255),
   REPONSEDUREE         int,
   REPONSEMESSAGE       varchar(255),
   primary key (REPONSEID)
);

/*==============================================================*/
/* Table : reponse_histo                                        */
/*==============================================================*/
create table reponse_histo
(
   REPONSEVALEUR        text,
   REPONSEDEBUT         int default NULL,
   REPONSEFIN           int default NULL,
   REPONSEHISTOID       int not null,
   PASSAGEID            int not null,
   PROPOSITIONID        int not null,
   primary key (REPONSEHISTOID)
);

/*==============================================================*/
/* Table : rubrique                                             */
/*==============================================================*/
create table rubrique
(
   RUBRIQUEID           int not null,
   TESTID               int not null,
   RUBRIQUENOM          varchar(255) default NULL,
   primary key (RUBRIQUEID)
);

/*==============================================================*/
/* Table : test                                                 */
/*==============================================================*/
create table test
(
   TESTID               int not null,
   CATEGORIEID          int not null,
   TESTMATIERE          varchar(255) default NULL,
   TESTDUREE            smallint default NULL,
   TEST_NBQUESTION_RUB  int default NULL,
   TESTNATURE           varchar(50) default NULL,
   TESTFORMAT           varchar(255) default NULL,
   TEST_START           text,
   TEST_DESCRIPTION     text,
   THEME                varchar(255) default NULL,
   NIVEAU               int default NULL,
   primary key (TESTID)
);

alter table JOINTURE add constraint FK_ASSOCIATION_12 foreign key (TESTID)
      references test (TESTID) on delete restrict on update restrict;

alter table JOINTURE add constraint FK_ASSOCIATION_13 foreign key (PASSAGEID)
      references passage (PASSAGEID) on delete restrict on update restrict;

alter table passage add constraint FK_ASSOCIATION2 foreign key (CANDIDATID)
      references candidat (CANDIDATID) on delete restrict on update restrict;

alter table proposition add constraint FK_ASSOCIATION7 foreign key (QUESTIONID)
      references question (QUESTIONID) on delete restrict on update restrict;

alter table question add constraint FK_ASSOCIATION8 foreign key (RUBRIQUEID)
      references rubrique (RUBRIQUEID) on delete restrict on update restrict;

alter table reponse add constraint FK_ASSOCIATION_10 foreign key (PROPOSITIONID)
      references proposition (PROPOSITIONID) on delete restrict on update restrict;

alter table reponse add constraint FK_ASSOCIATION_11 foreign key (PASSAGEID)
      references passage (PASSAGEID) on delete restrict on update restrict;

alter table reponse_histo add constraint FK_ASSOCIATION10 foreign key (PROPOSITIONID)
      references proposition (PROPOSITIONID) on delete restrict on update restrict;

alter table reponse_histo add constraint FK_ASSOCIATION5 foreign key (PASSAGEID)
      references passage (PASSAGEID) on delete restrict on update restrict;

alter table rubrique add constraint FK_ASSOCIATION4 foreign key (TESTID)
      references test (TESTID) on delete restrict on update restrict;

alter table test add constraint FK_ASSOCIATION1 foreign key (CATEGORIEID)
      references categorie (CATEGORIEID) on delete restrict on update restrict;

