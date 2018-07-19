create table customer (
        id integer not null auto_increment,
        city varchar(255),
        created_at datetime not null,
        email varchar(255) not null,
        f_name varchar(255) not null,
        l_name varchar(255) not null,
        pan varchar(255),
        phone varchar(255) not null,
        wallet_id integer,
        primary key (id)
    );
    
    create table transaction (
        id bigint not null auto_increment,
        amount integer,
        created_at datetime not null,
        is_cancelled bit,
        linked_trx bigint,
        trx_type varchar(255) not null,
        customer_id integer,
        wallet_id integer,
        primary key (id)
    );
    
    create table wallet (
        id integer not null auto_increment,
        created_at datetime not null,
        current_bal integer default 0,
        wallet_type_id integer,
        primary key (id)
    );
    
    create table wallet_type (
        id integer not null auto_increment,
        created_at datetime not null,
        max_bal_allowed integer default 100000,
        max_trx_amt integer default 1000,
        max_trx_limit_amt integer default 100000,
        max_trx_limit_count integer default 100,
        max_trx_limit_duration_in_days integer default 30,
        min_bal_allowed integer default 0,
        min_trx_amt integer default 0,
        wt_name varchar(255) not null,
        primary key (id)
    );
    
    alter table customer add constraint UK_dwk6cx0afu8bs9o4t536v1j5v unique (email);
    alter table customer add constraint UK_4fcyh8b2t9mbm6dxks800o5g unique (pan);
    alter table customer add constraint UK_o3uty20c6csmx5y4uk2tc5r4m unique (phone);
    alter table wallet_type add constraint UK_d7xp2e7by6ns6euv3tyiarb1g unique (wt_name);
    
    alter table customer 
        add constraint FKgadeaynu91oljdr3ofje0jt4y 
        foreign key (wallet_id) 
        references wallet (id);
    
    alter table transaction 
        add constraint FKnbpjofb5abhjg5hiovi0t3k57 
        foreign key (customer_id) 
        references customer (id);
        
        
   alter table transaction 
        add constraint FKtfwlfspv2h4wcgc9rjd1658a6 
        foreign key (wallet_id) 
        references wallet (id);
        
        
   alter table wallet 
        add constraint FKqcki7lqle5mel0x09ecpbai64 
        foreign key (wallet_type_id) 
        references wallet_type (id);
    
   