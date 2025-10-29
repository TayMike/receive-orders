-- ========================================
-- 1. APARTAMENTO
-- ========================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE apartamento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    torre CHAR(1) NOT NULL CHECK (torre ~ '^[A-Z]$'),
    numero SMALLINT NOT NULL CHECK (numero >= 1 AND numero <= 127),
    andar SMALLINT NOT NULL CHECK (andar >= 1 AND andar <= 127),

    CONSTRAINT uk_apartamento UNIQUE (torre, andar, numero)
);

-- ========================================
-- 2. MORADOR
-- ========================================
CREATE TABLE morador (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cpf CHAR(11) NOT NULL UNIQUE CHECK (cpf ~ '^\d{11}$'),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL CHECK (email ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$')
);

CREATE INDEX idx_morador_cpf ON morador(cpf);

-- Telefones
CREATE TABLE morador_telefones (
    morador_id UUID NOT NULL REFERENCES morador(id) ON DELETE CASCADE,
    telefone VARCHAR(20) NOT NULL CHECK (telefone ~ '^\d{10,15}$'),
    PRIMARY KEY (morador_id, telefone)
);

-- Apartamentos do morador
CREATE TABLE morador_apartamentos (
    morador_id UUID NOT NULL REFERENCES morador(id) ON DELETE CASCADE,
    apartamento_ids UUID NOT NULL REFERENCES apartamento(id) ON DELETE CASCADE,
    PRIMARY KEY (morador_id, apartamento_ids)
);

CREATE INDEX idx_morador_apartamento ON morador_apartamentos(apartamento_ids);

-- ========================================
-- 3. RECEBIMENTO_ENCOMENDA
-- ========================================
CREATE TABLE recebimento_encomenda (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    apartamento_id UUID NOT NULL REFERENCES apartamento(id) ON DELETE RESTRICT,

    descricao VARCHAR(255) NOT NULL,

    data_entrega TIMESTAMPTZ NOT NULL CHECK (data_entrega <= NOW()),

    estado_coleta VARCHAR(20) NOT NULL
        CHECK (estado_coleta IN ('PENDENTE', 'COLETADA'))
);

CREATE INDEX idx_apartamento_id ON recebimento_encomenda(apartamento_id);
CREATE INDEX idx_data_entrega ON recebimento_encomenda(data_entrega DESC);
CREATE INDEX idx_estado_coleta ON recebimento_encomenda(estado_coleta);

-- ========================================
-- 4. COLETA_ENCOMENDA
-- ========================================
CREATE TABLE coleta_encomenda (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    recebimento_encomenda_id UUID NOT NULL
        REFERENCES recebimento_encomenda(id) ON DELETE RESTRICT,

    cpf_morador_coleta CHAR(11) NOT NULL CHECK (cpf_morador_coleta ~ '^\d{11}$'),

    nome_morador_coleta VARCHAR(100) NOT NULL,

    data_coleta TIMESTAMPTZ NOT NULL CHECK (data_coleta <= NOW())
);

CREATE INDEX idx_cpf_morador_coleta ON coleta_encomenda(cpf_morador_coleta);

-- ========================================
-- 5. NOTIFICACAO
-- ========================================
CREATE TABLE notificacao (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    apartamento_id UUID NOT NULL REFERENCES apartamento(id) ON DELETE CASCADE,

    mensagem VARCHAR(1000) NOT NULL,

    data_envio TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    lido BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_notificacao_apartamento ON notificacao(apartamento_id);
CREATE INDEX idx_notificacao_data_envio ON notificacao(data_envio DESC);
CREATE INDEX idx_notificacao_lido ON notificacao(lido);

-- ========================================
-- 6. NOTIFICACAO_LEITURA
-- ========================================
CREATE TABLE notificacao_leitura (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    notificacao_id UUID NOT NULL REFERENCES notificacao(id) ON DELETE CASCADE,
    morador_id UUID NOT NULL REFERENCES morador(id) ON DELETE CASCADE,

    lido_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    ip_address VARCHAR(45),
    user_agent VARCHAR(500),

    CONSTRAINT uk_notificacao_morador UNIQUE (notificacao_id, morador_id)
);

CREATE INDEX idx_notificacao_leitura_notificacao ON notificacao_leitura(notificacao_id);
CREATE INDEX idx_notificacao_leitura_morador ON notificacao_leitura(morador_id);
CREATE INDEX idx_notificacao_leitura_lido_em ON notificacao_leitura(lido_em DESC);