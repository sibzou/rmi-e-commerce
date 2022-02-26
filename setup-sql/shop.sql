create table item (
    id int,
    name text,
    price real,

    primary key(id)
);

-- données d'exemple
insert into item values (1, "Carte SD Dell",          50),
                        (2, "Disque dur Seagate 1To", 80),
                        (3, "Carte graphique 3080",   999.99);
