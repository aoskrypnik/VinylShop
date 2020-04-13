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

--
-- Name: getstatistic(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.getstatistic(year character varying) RETURNS TABLE(month_id integer, start_date timestamp without time zone, end_date timestamp without time zone, check_num bigint, income numeric, avg_check bigint, proceeds bigint)
    LANGUAGE sql IMMUTABLE STRICT
    AS $$
WITH RECURSIVE stat(month_id, start_date, end_date, check_num, income, avg_check, proceeds) AS
                   (SELECT 1,
                           year::timestamp,
                           year::timestamp + interval '1 month' - interval '1 day',
                           (SELECT COUNT(*) AS check_num
                            FROM cheq
                            WHERE date_time BETWEEN year::timestamp AND (year::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT SUM(r.sell_price * ((100 - c.check_discount) * 0.01) - r.purchase_price) AS income
                            FROM cheq c
                                     INNER JOIN record r on c.check_num = r.check_num
                            WHERE c.date_time BETWEEN year::timestamp AND (year::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT (SUM(sum_with_discount) / COUNT(*)) AS avg_check
                            FROM cheq
                            WHERE date_time BETWEEN year::timestamp AND (year::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT SUM(c.sum_with_discount) AS statistics_res
                            FROM cheq c
                            WHERE date_time BETWEEN year::timestamp AND (year::timestamp + interval '1 month' - interval '1 day'))

                    UNION
                    SELECT month_id + 1,
                           start_date + interval '1 month',
                           start_date + interval '2 month' - interval '1 day',
                           (SELECT COUNT(*) AS statistics_res
                            FROM cheq
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT SUM(r.sell_price * ((100 - c.check_discount) * 0.01) - r.purchase_price) AS income
                            FROM cheq c
                                     INNER JOIN record r on c.check_num = r.check_num
                            WHERE c.date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT (SUM(sum_with_discount) / COUNT(*)) AS avg_check
                            FROM cheq
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT SUM(c.sum_with_discount) AS statistics_res
                            FROM cheq c
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month')
                    FROM stat
                    WHERE month_id < 12)
SELECT *
FROM stat
ORDER BY month_id ASC;
$$;


ALTER FUNCTION public.getstatistic(year character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: album; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.album (
    catalog_num character varying(15) NOT NULL,
    release_year smallint NOT NULL,
    album_name character varying(100) NOT NULL,
    various_artists boolean NOT NULL
);


ALTER TABLE public.album OWNER TO postgres;

--
-- Name: albumgenre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.albumgenre (
    album_catalog_num character varying(15) NOT NULL,
    genre_name character varying(30) NOT NULL
);


ALTER TABLE public.albumgenre OWNER TO postgres;

--
-- Name: artist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist (
    artist_alias character varying(100) NOT NULL,
    activity boolean,
    country character(2) NOT NULL,
    artist_name character varying(50),
    birth_date date,
    death_date date
);


ALTER TABLE public.artist OWNER TO postgres;

--
-- Name: artist2band; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist2band (
    artist_alias character varying(100) NOT NULL,
    band_alias character varying(100) NOT NULL,
    join_date date NOT NULL,
    exit_date date,
    CONSTRAINT join_date_less_exit_date CHECK ((join_date < exit_date))
);


ALTER TABLE public.artist2band OWNER TO postgres;

--
-- Name: artist2track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist2track (
    track_catalog_num character varying(15) NOT NULL,
    artist_alias character varying(100) NOT NULL,
    featuring boolean NOT NULL
);


ALTER TABLE public.artist2track OWNER TO postgres;

--
-- Name: band; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.band (
    band_alias character varying(100) NOT NULL,
    activity boolean,
    country character(2) NOT NULL,
    start_year date NOT NULL,
    end_year date
);


ALTER TABLE public.band OWNER TO postgres;

--
-- Name: band2track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.band2track (
    track_catalog_num character varying(15) NOT NULL,
    band_alias character varying(100) NOT NULL,
    featuring boolean NOT NULL
);


ALTER TABLE public.band2track OWNER TO postgres;

--
-- Name: barcode_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.barcode_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.barcode_sequence OWNER TO postgres;

--
-- Name: cheq; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cheq (
    check_num integer NOT NULL,
    salesman_tab_num integer NOT NULL,
    customer_num integer,
    date_time timestamp without time zone NOT NULL,
    overall_sum integer NOT NULL,
    check_discount smallint,
    sum_with_discount integer NOT NULL
);


ALTER TABLE public.cheq OWNER TO postgres;

--
-- Name: check_check_num_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.check_check_num_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.check_check_num_seq OWNER TO postgres;

--
-- Name: check_check_num_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.check_check_num_seq OWNED BY public.cheq.check_num;


--
-- Name: composer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.composer (
    composer_name character varying(50) NOT NULL,
    country character(2) NOT NULL,
    activity_start date NOT NULL,
    activity_end date
);


ALTER TABLE public.composer OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_num integer NOT NULL,
    customer_name character varying(50) NOT NULL,
    email character varying(50),
    discount smallint NOT NULL
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_customer_num_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_customer_num_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_customer_num_seq OWNER TO postgres;

--
-- Name: customer_customer_num_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_customer_num_seq OWNED BY public.customer.customer_num;


--
-- Name: customer_phone_number; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_phone_number (
    phone_number character varying(15) NOT NULL,
    customer_num integer NOT NULL
);


ALTER TABLE public.customer_phone_number OWNER TO postgres;

--
-- Name: record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.record (
    bar_code character varying(15) NOT NULL,
    release_bar_code character varying(15) NOT NULL,
    check_num integer,
    supplier_edrpou character(8) NOT NULL,
    purchase_date date NOT NULL,
    purchase_price integer NOT NULL,
    sell_price integer NOT NULL,
    state character varying(3) NOT NULL,
    state_check_date date NOT NULL,
    CONSTRAINT state_check_date_less_equal_now CHECK ((state_check_date <= now())),
    CONSTRAINT state_check_date_more_equal_purchase_date CHECK ((state_check_date >= purchase_date))
);


ALTER TABLE public.record OWNER TO postgres;

--
-- Name: release; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.release (
    bar_code character varying(15) NOT NULL,
    album_catalog_num character varying(15) NOT NULL,
    country character(2) NOT NULL,
    release_date date NOT NULL,
    record_size smallint NOT NULL,
    record_speed smallint NOT NULL,
    copies_cnt integer NOT NULL,
    repress boolean NOT NULL,
    label character varying(30)
);


ALTER TABLE public.release OWNER TO postgres;

--
-- Name: salesman; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.salesman (
    tab_num integer NOT NULL,
    salesman_name character varying(50) NOT NULL,
    passport_num character varying(12) NOT NULL,
    address_city character varying(20) NOT NULL,
    address_str character varying(50) NOT NULL,
    address_home character varying(5) NOT NULL,
    address_apt smallint,
    phone_num character varying(15) NOT NULL,
    works_from date NOT NULL,
    works_to date,
    salary integer NOT NULL,
    login character varying(20),
    CONSTRAINT works_to_greater_than_works_from_constraint CHECK ((works_to > works_from))
);


ALTER TABLE public.salesman OWNER TO postgres;

--
-- Name: salesman_tab_num_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.salesman_tab_num_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.salesman_tab_num_seq OWNER TO postgres;

--
-- Name: salesman_tab_num_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.salesman_tab_num_seq OWNED BY public.salesman.tab_num;


--
-- Name: supplier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supplier (
    edrpou character(8) NOT NULL,
    address character varying(100) NOT NULL,
    phone_num character varying(15) NOT NULL
);


ALTER TABLE public.supplier OWNER TO postgres;

--
-- Name: track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track (
    catalog_num character varying(15) NOT NULL,
    track_name character varying(100) NOT NULL,
    duration integer NOT NULL
);


ALTER TABLE public.track OWNER TO postgres;

--
-- Name: track2album; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track2album (
    track_catalog_num character varying(15) NOT NULL,
    album_catalog_num character varying(15) NOT NULL
);


ALTER TABLE public.track2album OWNER TO postgres;

--
-- Name: track2composer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track2composer (
    track_catalog_num character varying(15) NOT NULL,
    composer_name character varying(50) NOT NULL
);


ALTER TABLE public.track2composer OWNER TO postgres;

--
-- Name: track_language; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track_language (
    track_catalog_num character varying(15) NOT NULL,
    lang_name character(2) NOT NULL
);


ALTER TABLE public.track_language OWNER TO postgres;

--
-- Name: usr_credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr_credentials (
    login character varying(20) NOT NULL,
    password character varying(100) NOT NULL,
    is_director boolean
);


ALTER TABLE public.usr_credentials OWNER TO postgres;

--
-- Name: cheq check_num; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cheq ALTER COLUMN check_num SET DEFAULT nextval('public.check_check_num_seq'::regclass);


--
-- Name: customer customer_num; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN customer_num SET DEFAULT nextval('public.customer_customer_num_seq'::regclass);


--
-- Name: salesman tab_num; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salesman ALTER COLUMN tab_num SET DEFAULT nextval('public.salesman_tab_num_seq'::regclass);



--
-- Name: barcode_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.barcode_sequence', 1, true);


--
-- Name: check_check_num_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.check_check_num_seq', 1, true);


--
-- Name: customer_customer_num_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_customer_num_seq', 1, true);


--
-- Name: salesman_tab_num_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.salesman_tab_num_seq', 1, true);


--
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (catalog_num);


--
-- Name: albumgenre albumgenre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.albumgenre
    ADD CONSTRAINT albumgenre_pkey PRIMARY KEY (album_catalog_num, genre_name);


--
-- Name: artist2band artist2band_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2band
    ADD CONSTRAINT artist2band_pkey PRIMARY KEY (artist_alias, band_alias, join_date);


--
-- Name: artist2track artist2track_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2track
    ADD CONSTRAINT artist2track_pk PRIMARY KEY (track_catalog_num, artist_alias);


--
-- Name: artist artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (artist_alias);


--
-- Name: band2track band2track_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.band2track
    ADD CONSTRAINT band2track_pk PRIMARY KEY (track_catalog_num, band_alias);


--
-- Name: band band_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.band
    ADD CONSTRAINT band_pkey PRIMARY KEY (band_alias);


--
-- Name: cheq check_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cheq
    ADD CONSTRAINT check_pkey PRIMARY KEY (check_num);


--
-- Name: cheq check_salesman_tab_num_date_time_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cheq
    ADD CONSTRAINT check_salesman_tab_num_date_time_key UNIQUE (salesman_tab_num, date_time);


--
-- Name: composer composer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composer
    ADD CONSTRAINT composer_pkey PRIMARY KEY (composer_name);


--
-- Name: customer_phone_number customer_phone_number_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_phone_number
    ADD CONSTRAINT customer_phone_number_pkey PRIMARY KEY (phone_number);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_num);


--
-- Name: record record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT record_pkey PRIMARY KEY (bar_code);


--
-- Name: release release_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.release
    ADD CONSTRAINT release_pkey PRIMARY KEY (bar_code);


--
-- Name: salesman salesman_passport_num_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salesman
    ADD CONSTRAINT salesman_passport_num_key UNIQUE (passport_num);


--
-- Name: salesman salesman_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salesman
    ADD CONSTRAINT salesman_pkey PRIMARY KEY (tab_num);


--
-- Name: supplier supplier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_pkey PRIMARY KEY (edrpou);


--
-- Name: track2album track2album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2album
    ADD CONSTRAINT track2album_pkey PRIMARY KEY (track_catalog_num, album_catalog_num);


--
-- Name: track2composer track2composer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2composer
    ADD CONSTRAINT track2composer_pkey PRIMARY KEY (track_catalog_num, composer_name);


--
-- Name: track_language track_language_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_language
    ADD CONSTRAINT track_language_pkey PRIMARY KEY (track_catalog_num, lang_name);


--
-- Name: track track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_pkey PRIMARY KEY (catalog_num);


--
-- Name: usr_credentials usr_credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr_credentials
    ADD CONSTRAINT usr_credentials_pkey PRIMARY KEY (login);


--
-- Name: albumgenre albumgenre_album_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.albumgenre
    ADD CONSTRAINT albumgenre_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE;


--
-- Name: artist2band artist2band_artist_artist_alias_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2band
    ADD CONSTRAINT artist2band_artist_artist_alias_fk FOREIGN KEY (artist_alias) REFERENCES public.artist(artist_alias) ON UPDATE CASCADE;


--
-- Name: artist2band artist2band_band_band_alias_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2band
    ADD CONSTRAINT artist2band_band_band_alias_fk FOREIGN KEY (band_alias) REFERENCES public.band(band_alias) ON UPDATE CASCADE;


--
-- Name: artist2track artist2track_artist_artist_alias_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2track
    ADD CONSTRAINT artist2track_artist_artist_alias_fk FOREIGN KEY (artist_alias) REFERENCES public.artist(artist_alias) ON UPDATE CASCADE;


--
-- Name: artist2track artist2track_track_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist2track
    ADD CONSTRAINT artist2track_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE;


--
-- Name: band2track band2track_band_band_alias_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.band2track
    ADD CONSTRAINT band2track_band_band_alias_fk FOREIGN KEY (band_alias) REFERENCES public.band(band_alias) ON UPDATE CASCADE;


--
-- Name: band2track band2track_track_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.band2track
    ADD CONSTRAINT band2track_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE;


--
-- Name: cheq check_customer_customer_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cheq
    ADD CONSTRAINT check_customer_customer_num_fk FOREIGN KEY (customer_num) REFERENCES public.customer(customer_num) ON DELETE SET NULL;


--
-- Name: cheq check_salesman_salesman_tab_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cheq
    ADD CONSTRAINT check_salesman_salesman_tab_num_fk FOREIGN KEY (salesman_tab_num) REFERENCES public.salesman(tab_num);


--
-- Name: customer_phone_number customer_phone_number_customer_customer_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_phone_number
    ADD CONSTRAINT customer_phone_number_customer_customer_num_fk FOREIGN KEY (customer_num) REFERENCES public.customer(customer_num) ON DELETE CASCADE;


--
-- Name: record record_check_check_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT record_check_check_num_fk FOREIGN KEY (check_num) REFERENCES public.cheq(check_num);


--
-- Name: record record_release_bar_code_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT record_release_bar_code_fk FOREIGN KEY (release_bar_code) REFERENCES public.release(bar_code);


--
-- Name: record record_supplier_edrpou_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT record_supplier_edrpou_fk FOREIGN KEY (supplier_edrpou) REFERENCES public.supplier(edrpou);


--
-- Name: release release_album_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.release
    ADD CONSTRAINT release_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE;


--
-- Name: salesman salesman_usr_credentials_login_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.salesman
    ADD CONSTRAINT salesman_usr_credentials_login_fk FOREIGN KEY (login) REFERENCES public.usr_credentials(login);


--
-- Name: track2album track2album_album_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2album
    ADD CONSTRAINT track2album_album_catalog_num_fk FOREIGN KEY (album_catalog_num) REFERENCES public.album(catalog_num) ON DELETE CASCADE;


--
-- Name: track2album track2album_track_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2album
    ADD CONSTRAINT track2album_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE;


--
-- Name: track2composer track2composer_composer_composer_name_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2composer
    ADD CONSTRAINT track2composer_composer_composer_name_fk FOREIGN KEY (composer_name) REFERENCES public.composer(composer_name);


--
-- Name: track2composer track2composer_track_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track2composer
    ADD CONSTRAINT track2composer_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE;


--
-- Name: track_language track_language_track_catalog_num_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_language
    ADD CONSTRAINT track_language_track_catalog_num_fk FOREIGN KEY (track_catalog_num) REFERENCES public.track(catalog_num) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

