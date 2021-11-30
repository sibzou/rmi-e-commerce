create table account (
    num text,
    cryptogram text,
    balance real,

    primary key(num)
);

-- données d'exemple
insert into account values ("8263920393848017", "393", 100),
                           ("2018342885251322", "897", 5),
                           ("5352639283022076", "469", 20);
