--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: album; Type: TABLE; Schema: public; Owner: vbozhko
--
CREATE TABLE public.usr_credentials (
    login character varying(20) PRIMARY KEY,
    password character varying(100) NOT NULL
);

CREATE TABLE public.salesman (
    tab_num serial PRIMARY KEY,
    salesman_name character varying(50) NOT NULL,
    passport_num character varying(12) NOT NULL UNIQUE,
    address_city character varying(20) NOT NULL,
    address_str character varying(50) NOT NULL,
    address_home character varying(5) NOT NULL,
    address_apt smallint,
    phone_num character varying(15) NOT NULL,
    works_from date NOT NULL,
    works_to date,
    salary money NOT NULL,
    PRIMARY KEY(tab_num)
);

CREATE TABLE public.customer (
    customer_num serial PRIMARY KEY,
    customer_name character varying(50) NOT NULL,
    email character varying(50),
    discount smallint NOT NULL
);

CREATE TABLE public.supplier (
    edrpou character(8) PRIMARY KEY,
    address character varying(100) NOT NULL,
    phone_num character varying(15) NOT NULL
);

CREATE TABLE public.customer_phone_number (
    phone_number character varying(15) PRIMARY KEY,
    customer_num integer NOT NULL,
    CONSTRAINT customer_phone_number_customer_customer_num_fk FOREIGN KEY (customer_num) REFERENCES public.customer(customer_num) ON DELETE CASCADE
);

CREATE TABLE public.album (
    catalog_num character varying(15) PRIMARY KEY,
    release_year smallint NOT NULL,
    album_name character varying(100) NOT NULL,
    various_artists boolean NOT NULL
);

CREATE TABLE public.release (
    bar_code character varying(15) PRIMARY KEY,
    album_catalog_num character varying(15) NOT NULL,
    country character(2) NOT NULL,
    release_date date NOT NULL,
    record_size smallint NOT NULL,
    record_speed smallint NOT NULL,
    copies_cnt integer NOT NULL,
    repress boolean NOT NULL,
    label character varying(30),
    CONSTRAINT release_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE
);

CREATE TABLE public.albumgenre (
    album_catalog_num character varying(15) NOT NULL,
    genre_name character varying(30) NOT NULL,
    PRIMARY KEY(album_catalog_num, genre_name),
    CONSTRAINT albumgenre_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE
);

CREATE TABLE public.artist (
    artist_alias character varying(100) PRIMARY KEY,
    activity boolean,
    country character(2) NOT NULL,
    artist_name character varying(50),
    birth_date date,
    death_date date
);

CREATE TABLE public.band (
    band_alias character varying(100) PRIMARY KEY,
    activity boolean,
    country character(2) NOT NULL,
    start_year date NOT NULL,
    end_year date
);

CREATE TABLE public.artist2band (
    artist_alias character varying(100),
    band_alias character varying(100) NOT NULL,
    join_date date NOT NULL,
    exit_date date,
    PRIMARY KEY(artist_alias, band_alias, join_date),
    CONSTRAINT artist2band_artist_artist_alias_fk FOREIGN KEY (artist_alias) REFERENCES public.artist(artist_alias) ON UPDATE CASCADE,
    CONSTRAINT artist2band_band_band_alias_fk FOREIGN KEY (band_alias) REFERENCES public.band(band_alias) ON UPDATE CASCADE
);

CREATE TABLE public."check" (
    check_num serial PRIMARY KEY,
    salesman_tab_num integer NOT NULL,
    customer_num integer,
    date_time timestamp without time zone NOT NULL,
    UNIQUE (salesman_tab_num, date_time),
    CONSTRAINT check_customer_customer_num_fk FOREIGN KEY (customer_num)
        REFERENCES public.customer(customer_num) ON DELETE SET NULL,
    CONSTRAINT check_salesman_salesman_tab_num_fk FOREIGN KEY (salesman_tab_num)
        REFERENCES public.salesman(tab_num)
);

CREATE TABLE public.cartridge (
    bar_code character(15) PRIMARY KEY,
    check_num integer,
    supplier_edrpou character(8) NOT NULL,
    manufacturer_name character varying(30) NOT NULL,
    model_num character varying(20) NOT NULL,
    prod_date date NOT NULL,
    prod_country character(2) NOT NULL,
    warranty_period date NOT NULL,
    purchase_date date NOT NULL,
    purchase_price int NOT NULL,
    sell_price int NOT NULL,
    available boolean NOT NULL,
    shape character varying(10) NOT NULL,
    material character varying(10) NOT NULL,
    capacity smallint NOT NULL,
    CONSTRAINT cartridge_check_check_num_fk FOREIGN KEY (check_num) REFERENCES public."check"(check_num),
    CONSTRAINT cartridge_manufacturer_name_manufacturer_name_fk FOREIGN KEY (manufacturer_name) REFERENCES public.supplier(edrpou) ON UPDATE CASCADE,
    CONSTRAINT cartridge_supplier_edrpou_fk FOREIGN KEY (supplier_edrpou) REFERENCES public.supplier(edrpou)
);

CREATE TABLE public.composer (
    composer_name character varying(50) PRIMARY KEY,
    country character(2) NOT NULL,
    activity_start date NOT NULL,
    activity_end date
);

CREATE TABLE public.manufacturer (
    manufacturer_name character varying(30) PRIMARY KEY,
    registration_country character(2) NOT NULL
);

CREATE TABLE public.performance (
    track_catalog_num character varying(15),
    artist_alias character varying(100),
    band_alias character varying(100),
    featuring boolean NOT NULL,
    PRIMARY KEY(track_catalog_num),
    CONSTRAINT performance_artist_alias_artist_alias_fk FOREIGN KEY (artist_alias) references public."artist"(artist_alias),
    CONSTRAINT performance_band_alias_band_alias_fk FOREIGN KEY (band_alias) references public."band"(band_alias)
);

CREATE TABLE public.player (
    bar_code character(15) PRIMARY KEY,
    check_num integer,
    supplier_edrpou character(8) NOT NULL,
    manufacturer_name character varying(30) NOT NULL,
    model_num character varying(20) NOT NULL,
    prod_date date NOT NULL,
    prod_country character(2) NOT NULL,
    warranty_period date NOT NULL,
    purchase_date date NOT NULL,
    purchase_price int NOT NULL,
    sell_price int NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT player_check_check_num_fk FOREIGN KEY (check_num) REFERENCES public."check"(check_num),
    CONSTRAINT player_manufacturer_manufacturer_name_fk FOREIGN KEY (manufacturer_name) REFERENCES public.manufacturer(manufacturer_name) ON UPDATE CASCADE,
    CONSTRAINT player_supplier_edrpou_fk FOREIGN KEY (supplier_edrpou) REFERENCES public.supplier(edrpou)
);

CREATE TABLE public.playersize (
    player_bar_code character(15),
    size smallint NOT NULL,
    PRIMARY KEY(player_bar_code, size),
    CONSTRAINT playersize_player_bar_code_fk FOREIGN KEY (player_bar_code) REFERENCES public.player(bar_code)
);

CREATE TABLE public.playerspeed (
    player_bar_code character(15),
    speed smallint NOT NULL,
    PRIMARY KEY(player_bar_code, speed),
    CONSTRAINT playerspeed_player_bar_code_fk FOREIGN KEY (player_bar_code) REFERENCES public.player(bar_code)
);

CREATE TABLE public.record (
    bar_code character(15) PRIMARY KEY,
    release_bar_code character varying(15) NOT NULL,
    check_num integer,
    supplier_edrpou character(8) NOT NULL,
    purchase_date date NOT NULL,
    purchase_price int NOT NULL,
    sell_price int NOT NULL,
    available boolean NOT NULL,
    state character varying(3) NOT NULL,
    state_check_date date NOT NULL,
    CONSTRAINT record_check_check_num_fk FOREIGN KEY (check_num) REFERENCES public."check"(check_num),
    CONSTRAINT record_release_bar_code_fk FOREIGN KEY (release_bar_code) REFERENCES public.release(bar_code),
    CONSTRAINT record_supplier_edrpou_fk FOREIGN KEY (supplier_edrpou) REFERENCES public.supplier(edrpou)
);

CREATE TABLE public.track (
    catalog_num character varying(15) PRIMARY KEY,
    track_name character varying(100) NOT NULL,
    duration int NOT NULL
);

CREATE TABLE public.track2album (
    track_catalog_num character varying(15),
    album_catalog_num character varying(15),
    PRIMARY KEY(track_catalog_num, album_catalog_num),
    CONSTRAINT track2album_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE,
    CONSTRAINT track2album_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE
);

CREATE TABLE public.track2composer (
    track_catalog_num character varying(15),
    composer_name character varying(50),
    PRIMARY KEY(track_catalog_num, composer_name),
    CONSTRAINT track2composer_composer_composer_name_fk FOREIGN KEY (composer_name) REFERENCES public.composer(composer_name),
    CONSTRAINT track2composer_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE
);

CREATE TABLE public.track_language (
    track_catalog_num character varying(15),
    lang_name character(2),
    PRIMARY KEY(track_catalog_num, lang_name),
    CONSTRAINT track_language_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE
);

CREATE TABLE public.warranty_service (
    warranty_service_num serial PRIMARY KEY,
    player_bar_code character varying(15),
    cartridge_bar_code character varying(15),
    take_date date NOT NULL,
    return_date date,
    expert_summary character varying(200),
    CONSTRAINT warranty_service_player_bar_code_fk FOREIGN KEY (player_bar_code) REFERENCES public.player(bar_code),
    CONSTRAINT warranty_service_cartridge_bar_code_fk FOREIGN KEY (cartridge_bar_code) REFERENCES public.cartridge(bar_code)
);

CREATE TABLE public.write_off (
    write_off_num serial PRIMARY KEY,
    record_bar_code character varying(15),
    player_bar_code character varying(15),
    cartridge_bar_code character varying(15),
    salesman_num integer NOT NULL,
    write_off_date date NOT NULL,
    fee int,
    reason character varying(200) NOT NULL,
    CONSTRAINT write_off_salesman_tab_num_fk FOREIGN KEY (salesman_num) REFERENCES public.salesman(tab_num),
    CONSTRAINT write_off_record_bar_code_fk FOREIGN KEY (record_bar_code) REFERENCES public.record(bar_code),
    CONSTRAINT write_off_player_bar_code_fk FOREIGN KEY (player_bar_code) REFERENCES public.player(bar_code),
    CONSTRAINT write_off_cartridge_bar_code_fk FOREIGN KEY (cartridge_bar_code) REFERENCES public.cartridge(bar_code)
);
