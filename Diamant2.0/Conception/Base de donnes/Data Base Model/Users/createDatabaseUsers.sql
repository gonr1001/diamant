/*==============================================================*/
/* Database name:  USERS_DATABASE                               */
/* DBMS name:      PostgreSQL 7                                 */
/* Created on:     2005-03-03 10:58:14                          */
/*==============================================================*/


drop index PERSON_PK;

drop index PERSON_ROLE_FK;

drop index ROLE_PK;

/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
create table PERSON (
PERSON_ID            INT4                 not null,
ROLE_ID              INT4                 null,
PASSWORD             VARCHAR(128)         null,
NAME                 VARCHAR(128)         null,
USERNAME             VARCHAR(128)         null,
constraint PK_PERSON primary key (PERSON_ID)
);

/*==============================================================*/
/* Index: PERSON_PK                                             */
/*==============================================================*/
create unique index PERSON_PK on PERSON (
PERSON_ID
);

/*==============================================================*/
/* Index: PERSON_ROLE_FK                                        */
/*==============================================================*/
create  index PERSON_ROLE_FK on PERSON (
ROLE_ID
);

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE (
ROLE_ID              INT4                 not null,
ROLE_NAME            VARCHAR(128)         null,
constraint PK_ROLE primary key (ROLE_ID)
);

/*==============================================================*/
/* Index: ROLE_PK                                               */
/*==============================================================*/
create unique index ROLE_PK on ROLE (
ROLE_ID
);

alter table PERSON
   add constraint FK_PERSON_PERSON_RO_ROLE foreign key (ROLE_ID)
      references ROLE (ROLE_ID)
      on delete restrict on update restrict;

