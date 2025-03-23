-- Table Pet
CREATE TABLE PET (
                     id_pet SERIAL PRIMARY KEY,
                     id_sensor VARCHAR(100) NOT NULL,
                     num_latitude DOUBLE PRECISION NOT NULL,
                     num_longitude DOUBLE PRECISION NOT NULL,
                     dat_datetime TIMESTAMP NOT NULL
);

COMMENT ON TABLE PET IS 'Tabela destinada aos registros das informações relativas ao PET';

COMMENT ON COLUMN PET.id_pet IS 'ID do pet';
COMMENT ON COLUMN PET.id_sensor IS 'ID do senso';
COMMENT ON COLUMN PET.num_latitude IS 'Numero da latitude';
COMMENT ON COLUMN PET.num_longitude IS 'Numero da longitude';
COMMENT ON COLUMN PET.dat_datetime IS 'Data do registro';

-- Table Location
CREATE TABLE LOCATION (
    id_location SERIAL PRIMARY KEY,
    nam_country VARCHAR(100),
    nam_state VARCHAR(100),
    nam_city VARCHAR(100),
    nam_neighborhood VARCHAR(100),
    nam_address VARCHAR(255),
    id_pet BIGINT NOT NULL,
    CONSTRAINT fk_pet FOREIGN KEY (id_pet) REFERENCES PET(id_pet)
);

COMMENT ON TABLE LOCATION IS 'Tabela destinada aos registros das localizações';

COMMENT ON COLUMN LOCATION.id_location IS 'ID da Localização registrada';
COMMENT ON COLUMN LOCATION.nam_country IS 'Nome do país';
COMMENT ON COLUMN LOCATION.nam_state IS 'Nome do estado';
COMMENT ON COLUMN LOCATION.nam_city IS 'Nome da cidade';
COMMENT ON COLUMN LOCATION.nam_neighborhood IS 'Nome do bairro';
COMMENT ON COLUMN LOCATION.nam_address IS 'Nome do endereço (Rua, Avenida, n° e etc)';