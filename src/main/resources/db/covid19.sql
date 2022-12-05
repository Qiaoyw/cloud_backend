create table arrivalFlightsChina
(
    id            int auto_increment
        primary key,
    number        varchar(255) null,
    beginning     varchar(255) null,
    arrival       varchar(255) null,
    terminal      varchar(255) null,
    scheduledTime varchar(255) null,
    actualTime    varchar(255) null,
    status        varchar(255) null
);

create table beginningFlightsChina
(
    id            int auto_increment
        primary key,
    number        varchar(255) null,
    beginning     varchar(255) null,
    arrival       varchar(255) null,
    terminal      varchar(255) null,
    scheduledTime varchar(255) null,
    actualTime    varchar(255) null,
    status        varchar(255) null
);

create table comment
(
    id          int auto_increment
        primary key,
    questionid  int  null,
    content     text null,
    time        text null,
    avatar      text null,
    username    text null,
    isauthority int  null,
    userid      int  null
);

create table flightToAbroad
(
    id            int auto_increment
        primary key,
    number        varchar(255) null,
    beginning     varchar(255) null,
    arrival       varchar(255) null,
    terminal      varchar(255) null,
    scheduledTime varchar(255) null,
    actualTime    varchar(255) null,
    status        varchar(255) null
);

create table flightToChina
(
    id            int auto_increment
        primary key,
    number        varchar(255) null,
    beginning     varchar(255) null,
    arrival       varchar(255) null,
    terminal      varchar(255) null,
    scheduledTime varchar(255) null,
    actualTime    varchar(255) null,
    status        varchar(255) null
);

create table latest_policy
(
    title   varchar(255) null,
    time    varchar(255) null,
    content longtext     null,
    id      int auto_increment
        primary key
);

create table news
(
    time  varchar(255) null,
    title varchar(255) null
);

create table province_policy
(
    id       int auto_increment
        primary key,
    province varchar(255) null,
    content  longtext     null
);

create table question
(
    userid  int  null,
    id      int auto_increment
        primary key,
    content text null,
    title   text null,
    time    text null
);

create table railway
(
    id         int auto_increment
        primary key,
    number     varchar(255) null,
    beginning  varchar(255) null,
    arrival    varchar(255) null,
    beginTime  varchar(255) null,
    arriveTime varchar(255) null,
    totalTime  varchar(255) null,
    distance   varchar(255) null
);

create table risk_area
(
    id        int auto_increment
        primary key,
    province  varchar(255) null,
    city      varchar(255) null,
    district  varchar(255) null,
    riskLevel varchar(255) null,
    address   varchar(255) null
);

create table three_code
(
    id               int auto_increment
        primary key,
    number           varchar(255) null,
    airportInChinese varchar(255) null,
    airportInEnglish varchar(255) null,
    countryInChinese varchar(255) null,
    countryInEnglish varchar(255) null
);

create table tips
(
    id      int auto_increment
        primary key,
    title   varchar(255) not null,
    content longtext     null
);

create table user
(
    id          int auto_increment
        primary key,
    username    varchar(255) null,
    email       varchar(255) null,
    password    varchar(255) null,
    avatar      varchar(255) null,
    isauthority int          null,
    constraint user_email_uindex
        unique (email)
);

