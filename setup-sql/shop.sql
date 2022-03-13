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
