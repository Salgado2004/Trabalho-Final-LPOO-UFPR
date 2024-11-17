INSERT INTO endereco (numero, logradouro, bairro, cidade) VALUES
('123', 'Rua XV de Novembro', 'Centro', 'Curitiba'),
('456', 'Avenida Sete de Setembro', 'Batel', 'Curitiba'),
('789', 'Rua Marechal Deodoro', 'Centro', 'Curitiba'),
('101', 'Rua das Flores', 'Centro', 'Curitiba'),
('202', 'Rua Comendador Araújo', 'Centro', 'Curitiba'),
('303', 'Rua João Gualberto', 'Alto da Glória', 'Curitiba'),
('404', 'Rua Mateus Leme', 'São Francisco', 'Curitiba'),
('505', 'Rua Visconde de Nácar', 'Centro', 'Curitiba'),
('606', 'Rua Conselheiro Laurindo', 'Centro', 'Curitiba'),
('707', 'Rua Barão do Rio Branco', 'Centro', 'Curitiba'),
('808', 'Rua Humberto de Campos', 'Centro', 'Pinhais'),
('909', 'Rua Jacob Macanhan', 'Centro', 'Pinhais'),
('1010', 'Rua Maria Antonieta dos Santos', 'Centro', 'Pinhais'),
('1111', 'Rua Rio Amazonas', 'Centro', 'Pinhais'),
('1212', 'Rua Rio Iguaçu', 'Centro', 'Pinhais');

INSERT INTO cliente (cpf, nome, sobrenome, rg, idEndereco) VALUES
('94831227080', 'Pedro', 'Souza', '129711280', 1),
('88543471087', 'Alisson', 'Santos', '119264210', 2),
('63287208071', 'Leonardo', 'Salgado', '129321481', 13),
('31057053090', 'Raul', 'Bana', '130632776', 3),
('12345678909', 'Maria', 'Silva', '123456789', 4),
('98765432100', 'João', 'Pereira', '987654321', 5),
('69429562010', 'Ana', 'Costa', '111222333', 6),
('45940918069', 'Carlos', 'Oliveira', '555666777', 7),
('38547351035', 'Fernanda', 'Almeida', '999888777', 8),
('90692230076', 'Paulo', 'Souza', '444555666', 9),
('82623549079', 'Juliana', 'Mendes', '222333444', 10);

INSERT INTO conta (cpfCliente, saldo) VALUES
('94831227080', 1000.00),
('88543471087', 1500.00),
('63287208071', 2000.00),
('31057053090', 2500.00),
('12345678909', 3000.00),
('98765432100', 3500.00);

INSERT INTO contaCorrente (numeroConta, limite, depositoInicial) VALUES
(1, 500.00, 1000.00),
(2, 1000.00, 1500.00),
(3, 1500.00, 2000.00);

INSERT INTO contaInvestimento (numeroConta, depositoInicial, depositoMinimo, montanteMinimo) VALUES
(4, 2000.00, 1000.00, 2000.00),
(5, 2500.00, 1500.00, 2500.00),
(6, 3000.00, 2000.00, 3000.00);

UPDATE cliente SET numeroConta = 1 WHERE cpf = '94831227080';
UPDATE cliente SET numeroConta = 2 WHERE cpf = '88543471087';
UPDATE cliente SET numeroConta = 3 WHERE cpf = '63287208071';
UPDATE cliente SET numeroConta = 4 WHERE cpf = '31057053090';
UPDATE cliente SET numeroConta = 5 WHERE cpf = '12345678909';
UPDATE cliente SET numeroConta = 6 WHERE cpf = '98765432100';