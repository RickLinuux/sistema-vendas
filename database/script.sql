create table Cliente
(
    ID_Cliente      int auto_increment
        primary key,
    Nome_Cliente    varchar(45)  null,
    Telefone        varchar(15)  null,
    Email           varchar(100) null,
    Data_Nascimento date         null
);

create table Fornecedor
(
    ID_Fornecedor   int auto_increment
        primary key,
    Nome_Fornecedor varchar(50)  null,
    Email           varchar(100) null,
    Telefone        varchar(15)  null,
    Endereco        varchar(100) null,
    Tipo            varchar(45)  null
);

create table Compra_Fornecedor
(
    ID_Compra     int auto_increment
        primary key,
    ID_Fornecedor int         not null,
    Categoria     varchar(45) not null,
    Quantidade    int         not null,
    Data_Compra   date        not null,
    Valor_Total   float       null,
    constraint Compra_Fornecedor_ibfk_1
        foreign key (ID_Fornecedor) references Fornecedor (ID_Fornecedor)
);

create index ID_Fornecedor
    on Compra_Fornecedor (ID_Fornecedor);

create table Produto
(
    ID_Produto        int auto_increment
        primary key,
    Nome_Produto      varchar(50)  null,
    Descricao_Produto varchar(100) null,
    Valor_Produto     float        null,
    Tipo_Produto      varchar(45)  null,
    ID_Fornecedor     int          null,
    constraint Produto_ibfk_1
        foreign key (ID_Fornecedor) references Fornecedor (ID_Fornecedor)
);

create table Estoque
(
    ID_Estoque   int auto_increment
        primary key,
    ID_Produto   int  not null,
    Quantidade   int  not null,
    Data_Entrega date not null,
    constraint Estoque_ibfk_1
        foreign key (ID_Produto) references Produto (ID_Produto)
);

create index ID_Produto
    on Estoque (ID_Produto);

create index ID_Fornecedor
    on Produto (ID_Fornecedor);

create table Venda
(
    ID_Venda    int auto_increment
        primary key,
    ID_Produto  int   not null,
    ID_Cliente  int   not null,
    Data_Venda  date  not null,
    Quantidade  int   not null,
    Valor_Final float null,
    constraint Venda_ibfk_1
        foreign key (ID_Produto) references Produto (ID_Produto),
    constraint Venda_ibfk_2
        foreign key (ID_Cliente) references Cliente (ID_Cliente)
);

create index ID_Cliente
    on Venda (ID_Cliente);

create index ID_Produto
    on Venda (ID_Produto);


