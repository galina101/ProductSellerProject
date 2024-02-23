drop table seller if exists;
drop table product if exists;


create table seller (
    seller_id int primary key,
    seller_name varchar(255) not null
);

create table product (
       product_id int primary key,
       product_name varchar(255),
       product_price decimal(20,2),
       seller_id int references seller (seller_id)
      -- seller_id int,
      -- foreign key seller_id references seller(seller_id)
);