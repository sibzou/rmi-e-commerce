create table item (
    id int,
    name text,
    price real,

    primary key(id)
);

create table cart (
    articleId int,
    purchaseQuantity int,

    primary key(articleId)
);

-- données d'exemple
insert into item values (1, "Carte SD Dell",          50),
                        (2, "Disque dur Seagate 1To", 80),
                        (3, "Carte graphique 3080",   999.99);

insert into cart values (3, 2),
                        (2, 4);
