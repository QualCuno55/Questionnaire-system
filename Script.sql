CREATE DATABASE Questionnaire;
USE Questionnaire;
create table "Topic"
(
    "Id"          INTEGER auto_increment,
    "Description" VARCHAR,
    "TopicName"   VARCHAR not null,
    constraint TOPIC_PK
        primary key ("Id")
);

create table "Questions"
(
    "Id"            INTEGER auto_increment,
    "Question"      VARCHAR not null,
    "Topic_Id"      INTEGER not null,
    "Question_type" VARCHAR not null,
    constraint QUESTIONS_PK
        primary key ("Id"),
    constraint QUESTIONS_TOPIC_ID_FK
        foreign key ("Topic_Id") references "Topic" ("Id")
            on update cascade on delete cascade
);

create table "Answers"
(
    "Id"          INTEGER auto_increment,
    "Answer"      VARCHAR not null,
    "Question_Id" INTEGER not null,
    constraint ANSWERS_PK
        primary key ("Id"),
    constraint ANSWERS_QUESTIONS_ID_FK
        foreign key ("Question_Id") references "Questions" ("Id")
            on update cascade on delete cascade
);


create table "Users"
(
    "Id"       INTEGER auto_increment,
    "Login"    VARCHAR not null,
    "Password" VARCHAR not null,
    "UserName" VARCHAR not null,
    "Role"     VARCHAR not null
);
create table "UsersAnswers"
(
    "Id"         INTEGER auto_increment,
    "UserId"     INTEGER not null,
    "TopicId"    INTEGER not null,
    "QuestionId" INTEGER not null,
    "AnswerId"   INTEGER not null,
    constraint USERSANSWERS_PK
        primary key ("Id"),
    constraint USERSANSWERS_ANSWERS_ID_FK
        foreign key ("AnswerId") references "Answers" ("Id")
            on update cascade on delete cascade,
    constraint USERSANSWERS_QUESTIONS_ID_FK
        foreign key ("QuestionId") references "Questions" ("Id")
            on update cascade on delete cascade,
    constraint USERSANSWERS_TOPIC_ID_FK
        foreign key ("TopicId") references "Topic" ("Id")
            on update cascade on delete cascade,
    constraint USERSANSWERS_USERS_ID_FK
        foreign key ("UserId") references "Users" ("Id")
            on update cascade on delete cascade
);
INSERT INTO "Users" ("Login","Password","UserName","Role")
VALUES ('admin', 'admin', 'MegaAdmin', 'Admin' );