--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.4

-- Started on 2019-07-26 09:31:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12278)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2841 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 36854)
-- Name: act_numbers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_numbers (
    id integer NOT NULL,
    number integer NOT NULL,
    type character varying(18)
);


ALTER TABLE public.act_numbers OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 36857)
-- Name: act_numbers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.act_numbers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.act_numbers_id_seq OWNER TO postgres;

--
-- TOC entry 2842 (class 0 OID 0)
-- Dependencies: 197
-- Name: act_numbers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.act_numbers_id_seq OWNED BY public.act_numbers.id;


--
-- TOC entry 198 (class 1259 OID 36859)
-- Name: action_time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.action_time (
    id integer NOT NULL,
    "time" timestamp without time zone,
    creator integer
);


ALTER TABLE public.action_time OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 36862)
-- Name: action_time_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.action_time_id_seq
    START WITH 1968
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.action_time_id_seq OWNER TO postgres;

--
-- TOC entry 2843 (class 0 OID 0)
-- Dependencies: 199
-- Name: action_time_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.action_time_id_seq OWNED BY public.action_time.id;


--
-- TOC entry 200 (class 1259 OID 36864)
-- Name: administration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administration (
    id integer NOT NULL,
    worker integer NOT NULL,
    duty boolean DEFAULT false NOT NULL
);


ALTER TABLE public.administration OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 36868)
-- Name: administration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.administration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.administration_id_seq OWNER TO postgres;

--
-- TOC entry 2844 (class 0 OID 0)
-- Dependencies: 201
-- Name: administration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.administration_id_seq OWNED BY public.administration.id;


--
-- TOC entry 202 (class 1259 OID 36870)
-- Name: analyses_cake; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analyses_cake (
    id integer NOT NULL,
    cellulose real,
    humidity real,
    oiliness real,
    protein real,
    create_time integer,
    creator integer
);


ALTER TABLE public.analyses_cake OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 36873)
-- Name: analyses_cake_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.analyses_cake_id_seq
    START WITH 1730
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analyses_cake_id_seq OWNER TO postgres;

--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 203
-- Name: analyses_cake_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.analyses_cake_id_seq OWNED BY public.analyses_cake.id;


--
-- TOC entry 322 (class 1259 OID 37772)
-- Name: analyses_meal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.analyses_meal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analyses_meal_id_seq OWNER TO postgres;

--
-- TOC entry 323 (class 1259 OID 37774)
-- Name: analyses_meal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analyses_meal (
    id integer DEFAULT nextval('public.analyses_meal_id_seq'::regclass) NOT NULL,
    cellulose real,
    humidity real,
    oiliness real,
    protein real,
    create_time integer,
    creator integer
);


ALTER TABLE public.analyses_meal OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 36875)
-- Name: analyses_oil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analyses_oil (
    id integer NOT NULL,
    acid_value real NOT NULL,
    color integer NOT NULL,
    humidity real NOT NULL,
    organoleptic boolean NOT NULL,
    peroxide real NOT NULL,
    phosphorus real NOT NULL,
    wax real NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL,
    soap boolean DEFAULT true NOT NULL
);


ALTER TABLE public.analyses_oil OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 36879)
-- Name: analyses_oil_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.analyses_oil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analyses_oil_id_seq OWNER TO postgres;

--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 205
-- Name: analyses_oil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.analyses_oil_id_seq OWNED BY public.analyses_oil.id;


--
-- TOC entry 206 (class 1259 OID 36881)
-- Name: analyses_sun; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analyses_sun (
    id integer NOT NULL,
    acid_value real,
    contamination boolean,
    humidity1 real,
    oil_impurity real,
    oiliness real,
    soreness real,
    create_time integer,
    creator integer,
    humidity2 real DEFAULT 0 NOT NULL,
    act integer DEFAULT '-1'::integer NOT NULL
);


ALTER TABLE public.analyses_sun OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 36886)
-- Name: analyses_sun_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.analyses_sun_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analyses_sun_id_seq OWNER TO postgres;

--
-- TOC entry 2847 (class 0 OID 0)
-- Dependencies: 207
-- Name: analyses_sun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.analyses_sun_id_seq OWNED BY public.analyses_sun.id;


--
-- TOC entry 208 (class 1259 OID 36888)
-- Name: app_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_settings (
    id integer NOT NULL,
    customer character varying(255),
    organisation integer
);


ALTER TABLE public.app_settings OWNER TO postgres;

--
-- TOC entry 317 (class 1259 OID 37742)
-- Name: archivator_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.archivator_data (
    id integer NOT NULL,
    type character varying(18) NOT NULL,
    document integer NOT NULL,
    _time timestamp without time zone NOT NULL
);


ALTER TABLE public.archivator_data OWNER TO postgres;

--
-- TOC entry 316 (class 1259 OID 37740)
-- Name: archivators_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.archivators_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.archivators_id_seq OWNER TO postgres;

--
-- TOC entry 2848 (class 0 OID 0)
-- Dependencies: 316
-- Name: archivators_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.archivators_id_seq OWNED BY public.archivator_data.id;


--
-- TOC entry 209 (class 1259 OID 36891)
-- Name: bot_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bot_settings (
    id integer NOT NULL,
    name character varying(255),
    token character varying(255)
);


ALTER TABLE public.bot_settings OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 36897)
-- Name: bot_uids; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bot_uids (
    id integer NOT NULL,
    term timestamp without time zone,
    uid character varying(255),
    worker integer
);


ALTER TABLE public.bot_uids OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 36900)
-- Name: bot_user_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bot_user_settings (
    id integer NOT NULL,
    analyses character varying(255),
    extraction boolean,
    kpo boolean,
    show boolean,
    telegram_id bigint,
    transport character varying(255),
    vro boolean,
    weight character varying(255),
    worker integer,
    language character varying(2) DEFAULT 'ru'::character varying NOT NULL
);


ALTER TABLE public.bot_user_settings OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 36907)
-- Name: bot_user_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bot_user_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bot_user_settings_id_seq OWNER TO postgres;

--
-- TOC entry 2849 (class 0 OID 0)
-- Dependencies: 212
-- Name: bot_user_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bot_user_settings_id_seq OWNED BY public.bot_user_settings.id;


--
-- TOC entry 213 (class 1259 OID 36909)
-- Name: change_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.change_log (
    id integer NOT NULL,
    document character varying(255),
    label character varying(255),
    "time" timestamp without time zone,
    creator integer
);


ALTER TABLE public.change_log OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 36915)
-- Name: change_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.change_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.change_log_id_seq OWNER TO postgres;

--
-- TOC entry 2850 (class 0 OID 0)
-- Dependencies: 214
-- Name: change_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.change_log_id_seq OWNED BY public.change_log.id;


--
-- TOC entry 215 (class 1259 OID 36917)
-- Name: changes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.changes (
    id integer NOT NULL,
    field character varying(255),
    new_value character varying(255),
    old_value character varying(255),
    log integer
);


ALTER TABLE public.changes OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 36923)
-- Name: changes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.changes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.changes_id_seq OWNER TO postgres;

--
-- TOC entry 2851 (class 0 OID 0)
-- Dependencies: 216
-- Name: changes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.changes_id_seq OWNED BY public.changes.id;


--
-- TOC entry 217 (class 1259 OID 36925)
-- Name: deals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deals (
    id integer NOT NULL,
    archive boolean,
    date date,
    date_to date,
    done real,
    price real,
    quantity real,
    type character varying(255),
    uid character varying(255),
    creator integer,
    visibility integer,
    organisation integer,
    product integer,
    unit integer
);


ALTER TABLE public.deals OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 36932)
-- Name: deals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.deals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.deals_id_seq OWNER TO postgres;

--
-- TOC entry 2852 (class 0 OID 0)
-- Dependencies: 218
-- Name: deals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.deals_id_seq OWNED BY public.deals.id;


--
-- TOC entry 219 (class 1259 OID 36934)
-- Name: document_organisations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.document_organisations (
    id integer NOT NULL,
    active boolean,
    value character varying(255)
);


ALTER TABLE public.document_organisations OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 36937)
-- Name: drivers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.drivers (
    id integer NOT NULL,
    license character varying(255),
    organisation integer,
    person integer,
    vehicle integer
);


ALTER TABLE public.drivers OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 36940)
-- Name: drivers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.drivers_id_seq OWNER TO postgres;

--
-- TOC entry 2853 (class 0 OID 0)
-- Dependencies: 221
-- Name: drivers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;


--
-- TOC entry 222 (class 1259 OID 36942)
-- Name: extraction_crude; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_crude (
    id integer NOT NULL,
    dissolvent real,
    fraction real,
    grease real,
    humidity real,
    humidity_income real,
    miscellas real,
    "time" timestamp without time zone,
    create_time integer,
    creator integer,
    turn integer
);


ALTER TABLE public.extraction_crude OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 36945)
-- Name: extraction_crude_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_crude_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_crude_id_seq OWNER TO postgres;

--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 223
-- Name: extraction_crude_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_crude_id_seq OWNED BY public.extraction_crude.id;


--
-- TOC entry 224 (class 1259 OID 36947)
-- Name: extraction_oil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_oil (
    id integer NOT NULL,
    acid real,
    explosion_t real,
    humidity real,
    peroxide real,
    phosphorus real,
    create_time integer,
    creator integer,
    turn integer
);


ALTER TABLE public.extraction_oil OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 36950)
-- Name: extraction_oil_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_oil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_oil_id_seq OWNER TO postgres;

--
-- TOC entry 2855 (class 0 OID 0)
-- Dependencies: 225
-- Name: extraction_oil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_oil_id_seq OWNED BY public.extraction_oil.id;


--
-- TOC entry 226 (class 1259 OID 36952)
-- Name: extraction_raw; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_raw (
    id integer NOT NULL,
    cellulose real,
    protein real,
    "time" timestamp without time zone,
    create_time integer,
    creator integer,
    turn integer
);


ALTER TABLE public.extraction_raw OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 36955)
-- Name: extraction_raw_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_raw_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_raw_id_seq OWNER TO postgres;

--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 227
-- Name: extraction_raw_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_raw_id_seq OWNED BY public.extraction_raw.id;


--
-- TOC entry 228 (class 1259 OID 36957)
-- Name: extraction_storage_grease; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_storage_grease (
    id integer NOT NULL,
    turn integer NOT NULL,
    "time" timestamp without time zone NOT NULL,
    storage integer NOT NULL,
    grease real DEFAULT 0 NOT NULL,
    humidity real NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.extraction_storage_grease OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 36961)
-- Name: extraction_storage_grease_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_storage_grease_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_storage_grease_id_seq OWNER TO postgres;

--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 229
-- Name: extraction_storage_grease_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_storage_grease_id_seq OWNED BY public.extraction_storage_grease.id;


--
-- TOC entry 230 (class 1259 OID 36963)
-- Name: extraction_storage_protein; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_storage_protein (
    id integer NOT NULL,
    turn integer NOT NULL,
    "time" timestamp without time zone NOT NULL,
    storage integer NOT NULL,
    protein real DEFAULT 0 NOT NULL,
    humidity real DEFAULT 0 NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.extraction_storage_protein OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 36968)
-- Name: extraction_storage_protein_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_storage_protein_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_storage_protein_id_seq OWNER TO postgres;

--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 231
-- Name: extraction_storage_protein_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_storage_protein_id_seq OWNED BY public.extraction_storage_protein.id;


--
-- TOC entry 232 (class 1259 OID 36970)
-- Name: extraction_turn_grease; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_turn_grease (
    id integer NOT NULL,
    turn integer NOT NULL,
    grease real DEFAULT 0 NOT NULL,
    humidity real DEFAULT 0 NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.extraction_turn_grease OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 36975)
-- Name: extraction_turn_grease_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_turn_grease_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_turn_grease_id_seq OWNER TO postgres;

--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 233
-- Name: extraction_turn_grease_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_turn_grease_id_seq OWNED BY public.extraction_turn_grease.id;


--
-- TOC entry 234 (class 1259 OID 36977)
-- Name: extraction_turn_protein; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extraction_turn_protein (
    id integer NOT NULL,
    turn integer NOT NULL,
    humidity real DEFAULT 0 NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL,
    protein real DEFAULT 0 NOT NULL
);


ALTER TABLE public.extraction_turn_protein OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 36982)
-- Name: extraction_turn_protein_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extraction_turn_protein_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extraction_turn_protein_id_seq OWNER TO postgres;

--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 235
-- Name: extraction_turn_protein_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extraction_turn_protein_id_seq OWNED BY public.extraction_turn_protein.id;


--
-- TOC entry 236 (class 1259 OID 36984)
-- Name: forpress; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forpress (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.forpress OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 36987)
-- Name: forpress_cake; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forpress_cake (
    id integer NOT NULL,
    humidity real,
    oiliness real,
    crude integer,
    forpress integer
);


ALTER TABLE public.forpress_cake OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 36990)
-- Name: forpress_cake_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forpress_cake_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forpress_cake_id_seq OWNER TO postgres;

--
-- TOC entry 2861 (class 0 OID 0)
-- Dependencies: 238
-- Name: forpress_cake_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forpress_cake_id_seq OWNED BY public.forpress_cake.id;


--
-- TOC entry 239 (class 1259 OID 36992)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 36994)
-- Name: kpo_parts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kpo_parts (
    id integer NOT NULL,
    date timestamp without time zone NOT NULL,
    organoleptic boolean DEFAULT false NOT NULL,
    color integer NOT NULL,
    acid real NOT NULL,
    peroxide real NOT NULL,
    soap boolean DEFAULT false NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL,
    number integer NOT NULL
);


ALTER TABLE public.kpo_parts OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 36999)
-- Name: kpo_parts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.kpo_parts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kpo_parts_id_seq OWNER TO postgres;

--
-- TOC entry 2862 (class 0 OID 0)
-- Dependencies: 241
-- Name: kpo_parts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.kpo_parts_id_seq OWNED BY public.kpo_parts.id;


--
-- TOC entry 242 (class 1259 OID 37001)
-- Name: turns_laboratory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turns_laboratory (
    id integer NOT NULL,
    turn integer NOT NULL
);


ALTER TABLE public.turns_laboratory OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 37004)
-- Name: laboratory_turns_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.laboratory_turns_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.laboratory_turns_id_seq OWNER TO postgres;

--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 243
-- Name: laboratory_turns_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.laboratory_turns_id_seq OWNED BY public.turns_laboratory.id;


--
-- TOC entry 325 (class 1259 OID 37782)
-- Name: laboratory_worker; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.laboratory_worker (
    id integer NOT NULL,
    turn integer NOT NULL,
    worker integer NOT NULL
);


ALTER TABLE public.laboratory_worker OWNER TO postgres;

--
-- TOC entry 324 (class 1259 OID 37780)
-- Name: laboratory_worker_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.laboratory_worker_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.laboratory_worker_id_seq OWNER TO postgres;

--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 324
-- Name: laboratory_worker_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.laboratory_worker_id_seq OWNED BY public.laboratory_worker.id;


--
-- TOC entry 244 (class 1259 OID 37006)
-- Name: load_plans; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.load_plans (
    id integer NOT NULL,
    canceled boolean,
    transport_customer character varying(255),
    date date,
    plan real,
    uid character varying(255),
    deal integer,
    document_organisation integer,
    transportation integer
);


ALTER TABLE public.load_plans OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 37012)
-- Name: load_plans_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.load_plans_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.load_plans_id_seq OWNER TO postgres;

--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 245
-- Name: load_plans_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.load_plans_id_seq OWNED BY public.load_plans.id;


--
-- TOC entry 246 (class 1259 OID 37014)
-- Name: oil_mass_fractions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oil_mass_fractions (
    id integer NOT NULL,
    turn integer NOT NULL,
    seed real DEFAULT 0 NOT NULL,
    seed_humidity real DEFAULT 0,
    husk real DEFAULT 0 NOT NULL,
    husk_humidity real DEFAULT 0,
    create_time integer NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.oil_mass_fractions OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 37021)
-- Name: oil_mass_fraction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.oil_mass_fraction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.oil_mass_fraction_id_seq OWNER TO postgres;

--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 247
-- Name: oil_mass_fraction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.oil_mass_fraction_id_seq OWNED BY public.oil_mass_fractions.id;


--
-- TOC entry 248 (class 1259 OID 37023)
-- Name: oil_mass_fractions_dry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.oil_mass_fractions_dry (
    id integer NOT NULL,
    turn integer NOT NULL,
    seed real DEFAULT 0 NOT NULL,
    husk real DEFAULT 0 NOT NULL,
    create_time integer NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.oil_mass_fractions_dry OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 37028)
-- Name: oil_mass_fractions_dry_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.oil_mass_fractions_dry_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.oil_mass_fractions_dry_id_seq OWNER TO postgres;

--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 249
-- Name: oil_mass_fractions_dry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.oil_mass_fractions_dry_id_seq OWNED BY public.oil_mass_fractions_dry.id;


--
-- TOC entry 250 (class 1259 OID 37030)
-- Name: organisation_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organisation_types (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.organisation_types OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 37033)
-- Name: organisation_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.organisation_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organisation_types_id_seq OWNER TO postgres;

--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 251
-- Name: organisation_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.organisation_types_id_seq OWNED BY public.organisation_types.id;


--
-- TOC entry 252 (class 1259 OID 37035)
-- Name: organisations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organisations (
    id integer NOT NULL,
    name character varying(255),
    type character varying(255)
);


ALTER TABLE public.organisations OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 37041)
-- Name: organisations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.organisations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organisations_id_seq OWNER TO postgres;

--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 253
-- Name: organisations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.organisations_id_seq OWNED BY public.organisations.id;


--
-- TOC entry 254 (class 1259 OID 37043)
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
    id integer NOT NULL,
    forename character varying(45),
    patronymic character varying(45),
    surname character varying(45) NOT NULL
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 37049)
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.persons_id_seq OWNER TO postgres;

--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 255
-- Name: persons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persons_id_seq OWNED BY public.persons.id;


--
-- TOC entry 256 (class 1259 OID 37051)
-- Name: phones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.phones (
    id integer NOT NULL,
    number character varying(255),
    person integer
);


ALTER TABLE public.phones OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 37054)
-- Name: phones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.phones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.phones_id_seq OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 257
-- Name: phones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.phones_id_seq OWNED BY public.phones.id;


--
-- TOC entry 321 (class 1259 OID 37758)
-- Name: probe_meal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.probe_meal (
    id integer NOT NULL,
    turn integer NOT NULL,
    manager character varying(45) NOT NULL,
    organisation character varying(45) NOT NULL,
    analyses integer NOT NULL
);


ALTER TABLE public.probe_meal OWNER TO postgres;

--
-- TOC entry 320 (class 1259 OID 37756)
-- Name: probe_meal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.probe_meal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.probe_meal_id_seq OWNER TO postgres;

--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 320
-- Name: probe_meal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.probe_meal_id_seq OWNED BY public.probe_meal.id;


--
-- TOC entry 258 (class 1259 OID 37056)
-- Name: probe_oil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.probe_oil (
    id integer NOT NULL,
    organisation character varying(45),
    analyses integer,
    turn integer,
    manager character varying(45)
);


ALTER TABLE public.probe_oil OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 37059)
-- Name: probe_oil_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.probe_oil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.probe_oil_id_seq OWNER TO postgres;

--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 259
-- Name: probe_oil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.probe_oil_id_seq OWNED BY public.probe_oil.id;


--
-- TOC entry 260 (class 1259 OID 37061)
-- Name: probe_oilcake; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.probe_oilcake (
    id integer NOT NULL,
    turn integer NOT NULL,
    organisation character varying(45),
    analyses integer NOT NULL,
    manager character varying(45)
);


ALTER TABLE public.probe_oilcake OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 37064)
-- Name: probe_oilcake_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.probe_oilcake_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.probe_oilcake_id_seq OWNER TO postgres;

--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 261
-- Name: probe_oilcake_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.probe_oilcake_id_seq OWNED BY public.probe_oilcake.id;


--
-- TOC entry 262 (class 1259 OID 37066)
-- Name: probe_sun; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.probe_sun (
    id integer NOT NULL,
    organisation character varying(45),
    analyses integer,
    turn integer,
    manager character varying(45)
);


ALTER TABLE public.probe_sun OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 37069)
-- Name: probe_sun_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.probe_sun_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.probe_sun_id_seq OWNER TO postgres;

--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 263
-- Name: probe_sun_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.probe_sun_id_seq OWNED BY public.probe_sun.id;


--
-- TOC entry 264 (class 1259 OID 37071)
-- Name: product_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_group (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.product_group OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 37074)
-- Name: product_properties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_properties (
    id integer NOT NULL,
    product integer NOT NULL,
    key character varying(18) NOT NULL,
    value character varying(45) NOT NULL
);


ALTER TABLE public.product_properties OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 37077)
-- Name: product_properties_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_properties_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_properties_id_seq OWNER TO postgres;

--
-- TOC entry 2876 (class 0 OID 0)
-- Dependencies: 266
-- Name: product_properties_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_properties_id_seq OWNED BY public.product_properties.id;


--
-- TOC entry 267 (class 1259 OID 37079)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    analyses character varying(255),
    name character varying(255),
    product_group integer
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 37085)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 2877 (class 0 OID 0)
-- Dependencies: 268
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 269 (class 1259 OID 37087)
-- Name: seals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seals (
    id integer NOT NULL,
    number character varying(255),
    batch integer,
    cargo integer
);


ALTER TABLE public.seals OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 37090)
-- Name: seals_batch; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seals_batch (
    id integer NOT NULL,
    created integer,
    archive boolean DEFAULT false NOT NULL,
    free integer DEFAULT 0 NOT NULL,
    title character varying(45),
    total integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.seals_batch OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 37096)
-- Name: seals_batch_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seals_batch_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seals_batch_id_seq OWNER TO postgres;

--
-- TOC entry 2878 (class 0 OID 0)
-- Dependencies: 271
-- Name: seals_batch_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seals_batch_id_seq OWNED BY public.seals_batch.id;


--
-- TOC entry 272 (class 1259 OID 37098)
-- Name: seals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seals_id_seq OWNER TO postgres;

--
-- TOC entry 2879 (class 0 OID 0)
-- Dependencies: 272
-- Name: seals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seals_id_seq OWNED BY public.seals.id;


--
-- TOC entry 273 (class 1259 OID 37100)
-- Name: storage_analyses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storage_analyses (
    id integer NOT NULL,
    turn integer NOT NULL,
    storage integer NOT NULL,
    oil integer NOT NULL
);


ALTER TABLE public.storage_analyses OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 37103)
-- Name: storage_analyses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storage_analyses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storage_analyses_id_seq OWNER TO postgres;

--
-- TOC entry 2880 (class 0 OID 0)
-- Dependencies: 274
-- Name: storage_analyses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storage_analyses_id_seq OWNED BY public.storage_analyses.id;


--
-- TOC entry 275 (class 1259 OID 37105)
-- Name: storage_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storage_product (
    id integer NOT NULL,
    storage integer NOT NULL,
    product integer NOT NULL
);


ALTER TABLE public.storage_product OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 37108)
-- Name: storage_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storage_product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storage_product_id_seq OWNER TO postgres;

--
-- TOC entry 2881 (class 0 OID 0)
-- Dependencies: 276
-- Name: storage_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storage_product_id_seq OWNED BY public.storage_product.id;


--
-- TOC entry 277 (class 1259 OID 37110)
-- Name: storage_turns; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storage_turns (
    id integer NOT NULL,
    turn integer NOT NULL
);


ALTER TABLE public.storage_turns OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 37113)
-- Name: storage_turns_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storage_turns_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storage_turns_id_seq OWNER TO postgres;

--
-- TOC entry 2882 (class 0 OID 0)
-- Dependencies: 278
-- Name: storage_turns_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storage_turns_id_seq OWNED BY public.storage_turns.id;


--
-- TOC entry 279 (class 1259 OID 37115)
-- Name: storages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storages (
    id integer NOT NULL,
    name character varying(45) NOT NULL
);


ALTER TABLE public.storages OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 37118)
-- Name: storages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storages_id_seq OWNER TO postgres;

--
-- TOC entry 2883 (class 0 OID 0)
-- Dependencies: 280
-- Name: storages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storages_id_seq OWNED BY public.storages.id;


--
-- TOC entry 281 (class 1259 OID 37120)
-- Name: subdivisions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subdivisions (
    id integer NOT NULL,
    key character varying(255),
    name character varying(255)
);


ALTER TABLE public.subdivisions OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 37126)
-- Name: trains; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trains (
    id integer NOT NULL,
    deal integer NOT NULL
);


ALTER TABLE public.trains OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 37129)
-- Name: trains_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trains_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trains_id_seq OWNER TO postgres;

--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 283
-- Name: trains_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trains_id_seq OWNED BY public.trains.id;


--
-- TOC entry 284 (class 1259 OID 37131)
-- Name: transportation_notes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transportation_notes (
    id integer NOT NULL,
    transportation integer NOT NULL,
    date timestamp without time zone NOT NULL,
    note character varying(255) NOT NULL,
    creator integer NOT NULL
);


ALTER TABLE public.transportation_notes OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 37134)
-- Name: transportation_notes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transportation_notes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transportation_notes_id_seq OWNER TO postgres;

--
-- TOC entry 2885 (class 0 OID 0)
-- Dependencies: 285
-- Name: transportation_notes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transportation_notes_id_seq OWNED BY public.transportation_notes.id;


--
-- TOC entry 286 (class 1259 OID 37136)
-- Name: transportations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transportations (
    id integer NOT NULL,
    uid character varying(255),
    creator integer,
    shipper integer NOT NULL,
    driver integer,
    time_in integer,
    time_out integer,
    vehicle integer,
    weight integer,
    sun_analyses integer,
    oil_analyses integer,
    meal_analyses integer,
    date date NOT NULL,
    counterparty integer NOT NULL,
    type character varying(8) NOT NULL,
    product integer NOT NULL,
    archive boolean DEFAULT false NOT NULL
);


ALTER TABLE public.transportations OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 37139)
-- Name: transportations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transportations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transportations_id_seq OWNER TO postgres;

--
-- TOC entry 2886 (class 0 OID 0)
-- Dependencies: 287
-- Name: transportations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transportations_id_seq OWNED BY public.transportations.id;


--
-- TOC entry 288 (class 1259 OID 37141)
-- Name: truck_transportation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.truck_transportation (
    id integer NOT NULL,
    truck bigint NOT NULL,
    transportation integer NOT NULL
);


ALTER TABLE public.truck_transportation OWNER TO postgres;

--
-- TOC entry 289 (class 1259 OID 37144)
-- Name: truck_transportation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.truck_transportation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.truck_transportation_id_seq OWNER TO postgres;

--
-- TOC entry 2887 (class 0 OID 0)
-- Dependencies: 289
-- Name: truck_transportation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.truck_transportation_id_seq OWNED BY public.truck_transportation.id;


--
-- TOC entry 290 (class 1259 OID 37146)
-- Name: truck_transportation_truck_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.truck_transportation_truck_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.truck_transportation_truck_seq OWNER TO postgres;

--
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 290
-- Name: truck_transportation_truck_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.truck_transportation_truck_seq OWNED BY public.truck_transportation.truck;


--
-- TOC entry 291 (class 1259 OID 37148)
-- Name: trucks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trucks (
    id integer NOT NULL,
    train integer NOT NULL,
    number character varying(45) NOT NULL
);


ALTER TABLE public.trucks OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 37151)
-- Name: trucks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trucks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trucks_id_seq OWNER TO postgres;

--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 292
-- Name: trucks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trucks_id_seq OWNED BY public.trucks.id;


--
-- TOC entry 293 (class 1259 OID 37153)
-- Name: turn_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turn_settings (
    id integer NOT NULL,
    _begin time without time zone,
    _end time without time zone,
    number integer
);


ALTER TABLE public.turn_settings OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 37156)
-- Name: turns; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turns (
    id integer NOT NULL,
    date timestamp without time zone NOT NULL,
    number integer NOT NULL
);


ALTER TABLE public.turns OWNER TO postgres;

--
-- TOC entry 295 (class 1259 OID 37159)
-- Name: turns_extraction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turns_extraction (
    id integer NOT NULL,
    turn integer
);


ALTER TABLE public.turns_extraction OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 37162)
-- Name: turns_extraction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turns_extraction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turns_extraction_id_seq OWNER TO postgres;

--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 296
-- Name: turns_extraction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turns_extraction_id_seq OWNED BY public.turns_extraction.id;


--
-- TOC entry 297 (class 1259 OID 37164)
-- Name: turns_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turns_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turns_id_seq OWNER TO postgres;

--
-- TOC entry 2891 (class 0 OID 0)
-- Dependencies: 297
-- Name: turns_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turns_id_seq OWNED BY public.turns.id;


--
-- TOC entry 319 (class 1259 OID 37750)
-- Name: turns_probes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turns_probes (
    id integer NOT NULL,
    turn integer NOT NULL
);


ALTER TABLE public.turns_probes OWNER TO postgres;

--
-- TOC entry 318 (class 1259 OID 37748)
-- Name: turns_probes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turns_probes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turns_probes_id_seq OWNER TO postgres;

--
-- TOC entry 2892 (class 0 OID 0)
-- Dependencies: 318
-- Name: turns_probes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turns_probes_id_seq OWNED BY public.turns_probes.id;


--
-- TOC entry 298 (class 1259 OID 37166)
-- Name: turns_vro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turns_vro (
    id integer NOT NULL,
    turn integer
);


ALTER TABLE public.turns_vro OWNER TO postgres;

--
-- TOC entry 299 (class 1259 OID 37169)
-- Name: turns_vro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turns_vro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turns_vro_id_seq OWNER TO postgres;

--
-- TOC entry 2893 (class 0 OID 0)
-- Dependencies: 299
-- Name: turns_vro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turns_vro_id_seq OWNED BY public.turns_vro.id;


--
-- TOC entry 300 (class 1259 OID 37171)
-- Name: uid; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.uid (
    uid character varying(36) NOT NULL
);


ALTER TABLE public.uid OWNER TO postgres;

--
-- TOC entry 301 (class 1259 OID 37174)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(45),
    password character varying(45),
    role character varying(16),
    uid character varying(36),
    worker integer,
    registrator integer
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 302 (class 1259 OID 37177)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2894 (class 0 OID 0)
-- Dependencies: 302
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 303 (class 1259 OID 37179)
-- Name: vehicles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles (
    id integer NOT NULL,
    model character varying(18),
    number character varying(14),
    trailer character varying(14),
    transporter integer
);


ALTER TABLE public.vehicles OWNER TO postgres;

--
-- TOC entry 304 (class 1259 OID 37182)
-- Name: vehicles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicles_id_seq OWNER TO postgres;

--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 304
-- Name: vehicles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicles_id_seq OWNED BY public.vehicles.id;


--
-- TOC entry 305 (class 1259 OID 37184)
-- Name: vro_crude; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vro_crude (
    id integer NOT NULL,
    humidity_after real,
    humidity_before real,
    huskiness real,
    kernel_offset real,
    pulp_humidity_1 real,
    soreness_after real,
    soreness_before real,
    "time" timestamp without time zone,
    create_time integer,
    creator integer,
    turn integer,
    pulp_humidity_2 real DEFAULT 0 NOT NULL
);


ALTER TABLE public.vro_crude OWNER TO postgres;

--
-- TOC entry 306 (class 1259 OID 37188)
-- Name: vro_crude_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vro_crude_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vro_crude_id_seq OWNER TO postgres;

--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 306
-- Name: vro_crude_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vro_crude_id_seq OWNED BY public.vro_crude.id;


--
-- TOC entry 307 (class 1259 OID 37190)
-- Name: vro_dailies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vro_dailies (
    id integer NOT NULL,
    husk_humidity real,
    husk_percent real,
    husk_soreness real,
    kernel_humidity real,
    kernel_percent real,
    create_time integer,
    creator integer,
    turn integer
);


ALTER TABLE public.vro_dailies OWNER TO postgres;

--
-- TOC entry 308 (class 1259 OID 37193)
-- Name: vro_dailys_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vro_dailys_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vro_dailys_id_seq OWNER TO postgres;

--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 308
-- Name: vro_dailys_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vro_dailys_id_seq OWNED BY public.vro_dailies.id;


--
-- TOC entry 309 (class 1259 OID 37195)
-- Name: vro_oil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vro_oil (
    id integer NOT NULL,
    acid real,
    color integer,
    peroxide real,
    phosphorus real,
    create_time integer,
    creator integer,
    turn integer
);


ALTER TABLE public.vro_oil OWNER TO postgres;

--
-- TOC entry 310 (class 1259 OID 37198)
-- Name: vro_oil_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vro_oil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vro_oil_id_seq OWNER TO postgres;

--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 310
-- Name: vro_oil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vro_oil_id_seq OWNED BY public.vro_oil.id;


--
-- TOC entry 311 (class 1259 OID 37200)
-- Name: weight_units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weight_units (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.weight_units OWNER TO postgres;

--
-- TOC entry 312 (class 1259 OID 37203)
-- Name: weights; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weights (
    id integer NOT NULL,
    brutto real,
    tara real,
    uid character varying(255),
    brutto_time integer,
    tara_time integer,
    correction real DEFAULT 0 NOT NULL
);


ALTER TABLE public.weights OWNER TO postgres;

--
-- TOC entry 313 (class 1259 OID 37207)
-- Name: weights_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.weights_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.weights_id_seq OWNER TO postgres;

--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 313
-- Name: weights_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.weights_id_seq OWNED BY public.weights.id;


--
-- TOC entry 314 (class 1259 OID 37209)
-- Name: workers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.workers (
    id integer NOT NULL,
    person integer,
    language character varying(2) DEFAULT 'ru'::character varying NOT NULL
);


ALTER TABLE public.workers OWNER TO postgres;

--
-- TOC entry 315 (class 1259 OID 37213)
-- Name: workers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.workers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.workers_id_seq OWNER TO postgres;

--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 315
-- Name: workers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.workers_id_seq OWNED BY public.workers.id;


--
-- TOC entry 2425 (class 2604 OID 37215)
-- Name: act_numbers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_numbers ALTER COLUMN id SET DEFAULT nextval('public.act_numbers_id_seq'::regclass);


--
-- TOC entry 2426 (class 2604 OID 37216)
-- Name: action_time id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.action_time ALTER COLUMN id SET DEFAULT nextval('public.action_time_id_seq'::regclass);


--
-- TOC entry 2428 (class 2604 OID 37217)
-- Name: administration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administration ALTER COLUMN id SET DEFAULT nextval('public.administration_id_seq'::regclass);


--
-- TOC entry 2429 (class 2604 OID 37218)
-- Name: analyses_cake id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_cake ALTER COLUMN id SET DEFAULT nextval('public.analyses_cake_id_seq'::regclass);


--
-- TOC entry 2431 (class 2604 OID 37219)
-- Name: analyses_oil id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_oil ALTER COLUMN id SET DEFAULT (nextval('public.analyses_oil_id_seq'::regclass))::regclass;


--
-- TOC entry 2434 (class 2604 OID 37220)
-- Name: analyses_sun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_sun ALTER COLUMN id SET DEFAULT (nextval('public.analyses_sun_id_seq'::regclass))::regclass;


--
-- TOC entry 2507 (class 2604 OID 37745)
-- Name: archivator_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivator_data ALTER COLUMN id SET DEFAULT nextval('public.archivators_id_seq'::regclass);


--
-- TOC entry 2436 (class 2604 OID 37221)
-- Name: bot_user_settings id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_user_settings ALTER COLUMN id SET DEFAULT (nextval('public.bot_user_settings_id_seq'::regclass))::regclass;


--
-- TOC entry 2437 (class 2604 OID 37222)
-- Name: change_log id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.change_log ALTER COLUMN id SET DEFAULT (nextval('public.change_log_id_seq'::regclass))::regclass;


--
-- TOC entry 2438 (class 2604 OID 37223)
-- Name: changes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.changes ALTER COLUMN id SET DEFAULT (nextval('public.changes_id_seq'::regclass))::regclass;


--
-- TOC entry 2439 (class 2604 OID 37224)
-- Name: deals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals ALTER COLUMN id SET DEFAULT (nextval('public.deals_id_seq'::regclass))::regclass;


--
-- TOC entry 2440 (class 2604 OID 37225)
-- Name: drivers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT (nextval('public.drivers_id_seq'::regclass))::regclass;


--
-- TOC entry 2441 (class 2604 OID 37226)
-- Name: extraction_crude id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_crude ALTER COLUMN id SET DEFAULT (nextval('public.extraction_crude_id_seq'::regclass))::regclass;


--
-- TOC entry 2442 (class 2604 OID 37227)
-- Name: extraction_oil id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_oil ALTER COLUMN id SET DEFAULT (nextval('public.extraction_oil_id_seq'::regclass))::regclass;


--
-- TOC entry 2443 (class 2604 OID 37228)
-- Name: extraction_raw id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_raw ALTER COLUMN id SET DEFAULT (nextval('public.extraction_raw_id_seq'::regclass))::regclass;


--
-- TOC entry 2445 (class 2604 OID 37229)
-- Name: extraction_storage_grease id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_storage_grease ALTER COLUMN id SET DEFAULT nextval('public.extraction_storage_grease_id_seq'::regclass);


--
-- TOC entry 2448 (class 2604 OID 37230)
-- Name: extraction_storage_protein id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_storage_protein ALTER COLUMN id SET DEFAULT nextval('public.extraction_storage_protein_id_seq'::regclass);


--
-- TOC entry 2451 (class 2604 OID 37231)
-- Name: extraction_turn_grease id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_turn_grease ALTER COLUMN id SET DEFAULT nextval('public.extraction_turn_grease_id_seq'::regclass);


--
-- TOC entry 2454 (class 2604 OID 37232)
-- Name: extraction_turn_protein id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_turn_protein ALTER COLUMN id SET DEFAULT nextval('public.extraction_turn_protein_id_seq'::regclass);


--
-- TOC entry 2455 (class 2604 OID 37233)
-- Name: forpress_cake id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forpress_cake ALTER COLUMN id SET DEFAULT (nextval('public.forpress_cake_id_seq'::regclass))::regclass;


--
-- TOC entry 2458 (class 2604 OID 37234)
-- Name: kpo_parts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kpo_parts ALTER COLUMN id SET DEFAULT nextval('public.kpo_parts_id_seq'::regclass);


--
-- TOC entry 2511 (class 2604 OID 37785)
-- Name: laboratory_worker id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laboratory_worker ALTER COLUMN id SET DEFAULT nextval('public.laboratory_worker_id_seq'::regclass);


--
-- TOC entry 2460 (class 2604 OID 37235)
-- Name: load_plans id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.load_plans ALTER COLUMN id SET DEFAULT (nextval('public.load_plans_id_seq'::regclass))::regclass;


--
-- TOC entry 2465 (class 2604 OID 37236)
-- Name: oil_mass_fractions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oil_mass_fractions ALTER COLUMN id SET DEFAULT nextval('public.oil_mass_fraction_id_seq'::regclass);


--
-- TOC entry 2468 (class 2604 OID 37237)
-- Name: oil_mass_fractions_dry id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oil_mass_fractions_dry ALTER COLUMN id SET DEFAULT nextval('public.oil_mass_fractions_dry_id_seq'::regclass);


--
-- TOC entry 2469 (class 2604 OID 37238)
-- Name: organisation_types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organisation_types ALTER COLUMN id SET DEFAULT (nextval('public.organisation_types_id_seq'::regclass))::regclass;


--
-- TOC entry 2470 (class 2604 OID 37239)
-- Name: organisations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organisations ALTER COLUMN id SET DEFAULT (nextval('public.organisations_id_seq'::regclass))::regclass;


--
-- TOC entry 2471 (class 2604 OID 37240)
-- Name: persons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons ALTER COLUMN id SET DEFAULT (nextval('public.persons_id_seq'::regclass))::regclass;


--
-- TOC entry 2472 (class 2604 OID 37241)
-- Name: phones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phones ALTER COLUMN id SET DEFAULT (nextval('public.phones_id_seq'::regclass))::regclass;


--
-- TOC entry 2509 (class 2604 OID 37761)
-- Name: probe_meal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_meal ALTER COLUMN id SET DEFAULT nextval('public.probe_meal_id_seq'::regclass);


--
-- TOC entry 2473 (class 2604 OID 37242)
-- Name: probe_oil id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_oil ALTER COLUMN id SET DEFAULT (nextval('public.probe_oil_id_seq'::regclass))::regclass;


--
-- TOC entry 2474 (class 2604 OID 37243)
-- Name: probe_oilcake id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_oilcake ALTER COLUMN id SET DEFAULT nextval('public.probe_oilcake_id_seq'::regclass);


--
-- TOC entry 2475 (class 2604 OID 37244)
-- Name: probe_sun id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_sun ALTER COLUMN id SET DEFAULT (nextval('public.probe_sun_id_seq'::regclass))::regclass;


--
-- TOC entry 2476 (class 2604 OID 37245)
-- Name: product_properties id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_properties ALTER COLUMN id SET DEFAULT nextval('public.product_properties_id_seq'::regclass);


--
-- TOC entry 2477 (class 2604 OID 37246)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT (nextval('public.products_id_seq'::regclass))::regclass;


--
-- TOC entry 2478 (class 2604 OID 37247)
-- Name: seals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals ALTER COLUMN id SET DEFAULT (nextval('public.seals_id_seq'::regclass))::regclass;


--
-- TOC entry 2482 (class 2604 OID 37248)
-- Name: seals_batch id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals_batch ALTER COLUMN id SET DEFAULT (nextval('public.seals_batch_id_seq'::regclass))::regclass;


--
-- TOC entry 2483 (class 2604 OID 37249)
-- Name: storage_analyses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_analyses ALTER COLUMN id SET DEFAULT nextval('public.storage_analyses_id_seq'::regclass);


--
-- TOC entry 2484 (class 2604 OID 37250)
-- Name: storage_product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_product ALTER COLUMN id SET DEFAULT nextval('public.storage_product_id_seq'::regclass);


--
-- TOC entry 2485 (class 2604 OID 37251)
-- Name: storage_turns id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_turns ALTER COLUMN id SET DEFAULT nextval('public.storage_turns_id_seq'::regclass);


--
-- TOC entry 2486 (class 2604 OID 37252)
-- Name: storages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storages ALTER COLUMN id SET DEFAULT nextval('public.storages_id_seq'::regclass);


--
-- TOC entry 2487 (class 2604 OID 37253)
-- Name: trains id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trains ALTER COLUMN id SET DEFAULT nextval('public.trains_id_seq'::regclass);


--
-- TOC entry 2488 (class 2604 OID 37254)
-- Name: transportation_notes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportation_notes ALTER COLUMN id SET DEFAULT nextval('public.transportation_notes_id_seq'::regclass);


--
-- TOC entry 2489 (class 2604 OID 37255)
-- Name: transportations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations ALTER COLUMN id SET DEFAULT (nextval('public.transportations_id_seq'::regclass))::regclass;


--
-- TOC entry 2491 (class 2604 OID 37256)
-- Name: truck_transportation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.truck_transportation ALTER COLUMN id SET DEFAULT nextval('public.truck_transportation_id_seq'::regclass);


--
-- TOC entry 2492 (class 2604 OID 37257)
-- Name: truck_transportation truck; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.truck_transportation ALTER COLUMN truck SET DEFAULT nextval('public.truck_transportation_truck_seq'::regclass);


--
-- TOC entry 2493 (class 2604 OID 37258)
-- Name: trucks id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trucks ALTER COLUMN id SET DEFAULT nextval('public.trucks_id_seq'::regclass);


--
-- TOC entry 2494 (class 2604 OID 37259)
-- Name: turns id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns ALTER COLUMN id SET DEFAULT nextval('public.turns_id_seq'::regclass);


--
-- TOC entry 2495 (class 2604 OID 37260)
-- Name: turns_extraction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_extraction ALTER COLUMN id SET DEFAULT (nextval('public.turns_extraction_id_seq'::regclass))::regclass;


--
-- TOC entry 2459 (class 2604 OID 37261)
-- Name: turns_laboratory id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_laboratory ALTER COLUMN id SET DEFAULT nextval('public.laboratory_turns_id_seq'::regclass);


--
-- TOC entry 2508 (class 2604 OID 37753)
-- Name: turns_probes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_probes ALTER COLUMN id SET DEFAULT nextval('public.turns_probes_id_seq'::regclass);


--
-- TOC entry 2496 (class 2604 OID 37262)
-- Name: turns_vro id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_vro ALTER COLUMN id SET DEFAULT (nextval('public.turns_vro_id_seq'::regclass))::regclass;


--
-- TOC entry 2497 (class 2604 OID 37263)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT (nextval('public.users_id_seq'::regclass))::regclass;


--
-- TOC entry 2498 (class 2604 OID 37264)
-- Name: vehicles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles ALTER COLUMN id SET DEFAULT (nextval('public.vehicles_id_seq'::regclass))::regclass;


--
-- TOC entry 2500 (class 2604 OID 37265)
-- Name: vro_crude id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_crude ALTER COLUMN id SET DEFAULT (nextval('public.vro_crude_id_seq'::regclass))::regclass;


--
-- TOC entry 2501 (class 2604 OID 37266)
-- Name: vro_dailies id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_dailies ALTER COLUMN id SET DEFAULT (nextval('public.vro_dailys_id_seq'::regclass))::regclass;


--
-- TOC entry 2502 (class 2604 OID 37267)
-- Name: vro_oil id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_oil ALTER COLUMN id SET DEFAULT (nextval('public.vro_oil_id_seq'::regclass))::regclass;


--
-- TOC entry 2504 (class 2604 OID 37268)
-- Name: weights id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weights ALTER COLUMN id SET DEFAULT (nextval('public.weights_id_seq'::regclass))::regclass;


--
-- TOC entry 2506 (class 2604 OID 37269)
-- Name: workers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.workers ALTER COLUMN id SET DEFAULT (nextval('public.workers_id_seq'::regclass))::regclass;


--
-- TOC entry 2513 (class 2606 OID 37271)
-- Name: act_numbers act_numbers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_numbers
    ADD CONSTRAINT act_numbers_pkey PRIMARY KEY (id);


--
-- TOC entry 2515 (class 2606 OID 37273)
-- Name: action_time action_time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.action_time
    ADD CONSTRAINT action_time_pkey PRIMARY KEY (id);


--
-- TOC entry 2517 (class 2606 OID 37275)
-- Name: administration administration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administration
    ADD CONSTRAINT administration_pkey PRIMARY KEY (id);


--
-- TOC entry 2519 (class 2606 OID 37277)
-- Name: analyses_cake analyses_cake_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_cake
    ADD CONSTRAINT analyses_cake_pkey PRIMARY KEY (id);


--
-- TOC entry 2651 (class 2606 OID 37779)
-- Name: analyses_meal analyses_meal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_meal
    ADD CONSTRAINT analyses_meal_pkey PRIMARY KEY (id);


--
-- TOC entry 2521 (class 2606 OID 37279)
-- Name: analyses_oil analyses_oil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_oil
    ADD CONSTRAINT analyses_oil_pkey PRIMARY KEY (id);


--
-- TOC entry 2523 (class 2606 OID 37281)
-- Name: analyses_sun analyses_sun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_sun
    ADD CONSTRAINT analyses_sun_pkey PRIMARY KEY (id);


--
-- TOC entry 2525 (class 2606 OID 37283)
-- Name: app_settings app_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_settings
    ADD CONSTRAINT app_settings_pkey PRIMARY KEY (id);


--
-- TOC entry 2645 (class 2606 OID 37747)
-- Name: archivator_data archivators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivator_data
    ADD CONSTRAINT archivators_pkey PRIMARY KEY (id);


--
-- TOC entry 2527 (class 2606 OID 37285)
-- Name: bot_settings bot_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_settings
    ADD CONSTRAINT bot_settings_pkey PRIMARY KEY (id);


--
-- TOC entry 2529 (class 2606 OID 37287)
-- Name: bot_uids bot_uids_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_uids
    ADD CONSTRAINT bot_uids_pkey PRIMARY KEY (id);


--
-- TOC entry 2531 (class 2606 OID 37289)
-- Name: bot_user_settings bot_user_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_user_settings
    ADD CONSTRAINT bot_user_settings_pkey PRIMARY KEY (id);


--
-- TOC entry 2533 (class 2606 OID 37291)
-- Name: change_log change_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.change_log
    ADD CONSTRAINT change_log_pkey PRIMARY KEY (id);


--
-- TOC entry 2535 (class 2606 OID 37293)
-- Name: changes changes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.changes
    ADD CONSTRAINT changes_pkey PRIMARY KEY (id);


--
-- TOC entry 2537 (class 2606 OID 37295)
-- Name: deals deals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT deals_pkey PRIMARY KEY (id);


--
-- TOC entry 2539 (class 2606 OID 37297)
-- Name: document_organisations document_organisations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.document_organisations
    ADD CONSTRAINT document_organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 2541 (class 2606 OID 37299)
-- Name: drivers drivers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pkey PRIMARY KEY (id);


--
-- TOC entry 2543 (class 2606 OID 37301)
-- Name: extraction_crude extraction_crude_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_crude
    ADD CONSTRAINT extraction_crude_pkey PRIMARY KEY (id);


--
-- TOC entry 2545 (class 2606 OID 37303)
-- Name: extraction_oil extraction_oil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_oil
    ADD CONSTRAINT extraction_oil_pkey PRIMARY KEY (id);


--
-- TOC entry 2547 (class 2606 OID 37305)
-- Name: extraction_raw extraction_raw_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_raw
    ADD CONSTRAINT extraction_raw_pkey PRIMARY KEY (id);


--
-- TOC entry 2549 (class 2606 OID 37307)
-- Name: extraction_storage_grease extraction_storage_grease_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_storage_grease
    ADD CONSTRAINT extraction_storage_grease_pkey PRIMARY KEY (id);


--
-- TOC entry 2551 (class 2606 OID 37309)
-- Name: extraction_storage_protein extraction_storage_protein_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_storage_protein
    ADD CONSTRAINT extraction_storage_protein_pkey PRIMARY KEY (id);


--
-- TOC entry 2553 (class 2606 OID 37311)
-- Name: extraction_turn_grease extraction_turn_grease_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_turn_grease
    ADD CONSTRAINT extraction_turn_grease_pkey PRIMARY KEY (id);


--
-- TOC entry 2555 (class 2606 OID 37313)
-- Name: extraction_turn_protein extraction_turn_protein_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_turn_protein
    ADD CONSTRAINT extraction_turn_protein_pkey PRIMARY KEY (id);


--
-- TOC entry 2559 (class 2606 OID 37315)
-- Name: forpress_cake forpress_cake_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forpress_cake
    ADD CONSTRAINT forpress_cake_pkey PRIMARY KEY (id);


--
-- TOC entry 2557 (class 2606 OID 37317)
-- Name: forpress forpress_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forpress
    ADD CONSTRAINT forpress_pkey PRIMARY KEY (id);


--
-- TOC entry 2561 (class 2606 OID 37319)
-- Name: kpo_parts kpo_parts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kpo_parts
    ADD CONSTRAINT kpo_parts_pkey PRIMARY KEY (id);


--
-- TOC entry 2563 (class 2606 OID 37321)
-- Name: turns_laboratory laboratory_turns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_laboratory
    ADD CONSTRAINT laboratory_turns_pkey PRIMARY KEY (id);


--
-- TOC entry 2653 (class 2606 OID 37787)
-- Name: laboratory_worker laboratory_worker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laboratory_worker
    ADD CONSTRAINT laboratory_worker_pkey PRIMARY KEY (id);


--
-- TOC entry 2565 (class 2606 OID 37323)
-- Name: load_plans load_plans_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.load_plans
    ADD CONSTRAINT load_plans_pkey PRIMARY KEY (id);


--
-- TOC entry 2567 (class 2606 OID 37325)
-- Name: oil_mass_fractions oil_mass_fraction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oil_mass_fractions
    ADD CONSTRAINT oil_mass_fraction_pkey PRIMARY KEY (id);


--
-- TOC entry 2569 (class 2606 OID 37327)
-- Name: oil_mass_fractions_dry oil_mass_fractions_dry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.oil_mass_fractions_dry
    ADD CONSTRAINT oil_mass_fractions_dry_pkey PRIMARY KEY (id);


--
-- TOC entry 2571 (class 2606 OID 37329)
-- Name: organisation_types organisation_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organisation_types
    ADD CONSTRAINT organisation_types_pkey PRIMARY KEY (id);


--
-- TOC entry 2573 (class 2606 OID 37331)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 2575 (class 2606 OID 37333)
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- TOC entry 2577 (class 2606 OID 37335)
-- Name: phones phones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phones
    ADD CONSTRAINT phones_pkey PRIMARY KEY (id);


--
-- TOC entry 2649 (class 2606 OID 37763)
-- Name: probe_meal probe_meal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_meal
    ADD CONSTRAINT probe_meal_pkey PRIMARY KEY (id);


--
-- TOC entry 2579 (class 2606 OID 37337)
-- Name: probe_oil probe_oil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_oil
    ADD CONSTRAINT probe_oil_pkey PRIMARY KEY (id);


--
-- TOC entry 2581 (class 2606 OID 37339)
-- Name: probe_oilcake probe_oilcake_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_oilcake
    ADD CONSTRAINT probe_oilcake_pkey PRIMARY KEY (id);


--
-- TOC entry 2583 (class 2606 OID 37341)
-- Name: probe_sun probe_sun_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_sun
    ADD CONSTRAINT probe_sun_pkey PRIMARY KEY (id);


--
-- TOC entry 2585 (class 2606 OID 37343)
-- Name: product_group product_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_group
    ADD CONSTRAINT product_group_pkey PRIMARY KEY (id);


--
-- TOC entry 2587 (class 2606 OID 37345)
-- Name: product_properties product_properties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_properties
    ADD CONSTRAINT product_properties_pkey PRIMARY KEY (id);


--
-- TOC entry 2589 (class 2606 OID 37347)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 2593 (class 2606 OID 37349)
-- Name: seals_batch seals_batch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals_batch
    ADD CONSTRAINT seals_batch_pkey PRIMARY KEY (id);


--
-- TOC entry 2591 (class 2606 OID 37351)
-- Name: seals seals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals
    ADD CONSTRAINT seals_pkey PRIMARY KEY (id);


--
-- TOC entry 2595 (class 2606 OID 37353)
-- Name: storage_analyses storage_analyses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_analyses
    ADD CONSTRAINT storage_analyses_pkey PRIMARY KEY (id);


--
-- TOC entry 2597 (class 2606 OID 37355)
-- Name: storage_product storage_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_product
    ADD CONSTRAINT storage_product_pkey PRIMARY KEY (id);


--
-- TOC entry 2599 (class 2606 OID 37357)
-- Name: storage_turns storage_turns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storage_turns
    ADD CONSTRAINT storage_turns_pkey PRIMARY KEY (id);


--
-- TOC entry 2601 (class 2606 OID 37359)
-- Name: storages storages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storages
    ADD CONSTRAINT storages_pkey PRIMARY KEY (id);


--
-- TOC entry 2605 (class 2606 OID 37361)
-- Name: subdivisions subdivisions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subdivisions
    ADD CONSTRAINT subdivisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2607 (class 2606 OID 37363)
-- Name: trains trains_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trains
    ADD CONSTRAINT trains_pkey PRIMARY KEY (id);


--
-- TOC entry 2609 (class 2606 OID 37365)
-- Name: transportation_notes transportation_notes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportation_notes
    ADD CONSTRAINT transportation_notes_pkey PRIMARY KEY (id);


--
-- TOC entry 2611 (class 2606 OID 37367)
-- Name: transportations transportations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT transportations_pkey PRIMARY KEY (id);


--
-- TOC entry 2613 (class 2606 OID 37369)
-- Name: truck_transportation truck_transportation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.truck_transportation
    ADD CONSTRAINT truck_transportation_pkey PRIMARY KEY (id);


--
-- TOC entry 2615 (class 2606 OID 37371)
-- Name: trucks trucks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trucks
    ADD CONSTRAINT trucks_pkey PRIMARY KEY (id);


--
-- TOC entry 2621 (class 2606 OID 37373)
-- Name: turns_extraction turns_extraction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_extraction
    ADD CONSTRAINT turns_extraction_pkey PRIMARY KEY (id);


--
-- TOC entry 2617 (class 2606 OID 37375)
-- Name: turn_settings turns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turn_settings
    ADD CONSTRAINT turns_pkey PRIMARY KEY (id);


--
-- TOC entry 2619 (class 2606 OID 37377)
-- Name: turns turns_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns
    ADD CONSTRAINT turns_pkey1 PRIMARY KEY (id);


--
-- TOC entry 2647 (class 2606 OID 37755)
-- Name: turns_probes turns_probes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_probes
    ADD CONSTRAINT turns_probes_pkey PRIMARY KEY (id);


--
-- TOC entry 2623 (class 2606 OID 37379)
-- Name: turns_vro turns_vro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turns_vro
    ADD CONSTRAINT turns_vro_pkey PRIMARY KEY (id);


--
-- TOC entry 2625 (class 2606 OID 37381)
-- Name: uid uid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uid
    ADD CONSTRAINT uid_pkey PRIMARY KEY (uid);


--
-- TOC entry 2603 (class 2606 OID 37383)
-- Name: storages unique_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storages
    ADD CONSTRAINT unique_id UNIQUE (id);


--
-- TOC entry 2627 (class 2606 OID 37717)
-- Name: users unique_uid; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_uid UNIQUE (uid);


--
-- TOC entry 2629 (class 2606 OID 37385)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2631 (class 2606 OID 37387)
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);


--
-- TOC entry 2633 (class 2606 OID 37389)
-- Name: vro_crude vro_crude_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_crude
    ADD CONSTRAINT vro_crude_pkey PRIMARY KEY (id);


--
-- TOC entry 2635 (class 2606 OID 37391)
-- Name: vro_dailies vro_dailys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_dailies
    ADD CONSTRAINT vro_dailys_pkey PRIMARY KEY (id);


--
-- TOC entry 2637 (class 2606 OID 37393)
-- Name: vro_oil vro_oil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_oil
    ADD CONSTRAINT vro_oil_pkey PRIMARY KEY (id);


--
-- TOC entry 2639 (class 2606 OID 37395)
-- Name: weight_units weight_units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weight_units
    ADD CONSTRAINT weight_units_pkey PRIMARY KEY (id);


--
-- TOC entry 2641 (class 2606 OID 37397)
-- Name: weights weights_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weights
    ADD CONSTRAINT weights_pkey PRIMARY KEY (id);


--
-- TOC entry 2643 (class 2606 OID 37399)
-- Name: workers workers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.workers
    ADD CONSTRAINT workers_pkey PRIMARY KEY (id);


--
-- TOC entry 2701 (class 2606 OID 37400)
-- Name: vro_crude fk1714re058pwrb9pb7kjvivf07; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_crude
    ADD CONSTRAINT fk1714re058pwrb9pb7kjvivf07 FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2661 (class 2606 OID 37405)
-- Name: app_settings fk1fpie18wyg86rhlfrgenrmcpp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_settings
    ADD CONSTRAINT fk1fpie18wyg86rhlfrgenrmcpp FOREIGN KEY (organisation) REFERENCES public.organisations(id);


--
-- TOC entry 2702 (class 2606 OID 37410)
-- Name: vro_crude fk28ipxf246dk1v3y8e33n6fbm7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_crude
    ADD CONSTRAINT fk28ipxf246dk1v3y8e33n6fbm7 FOREIGN KEY (turn) REFERENCES public.turns_vro(id);


--
-- TOC entry 2710 (class 2606 OID 37415)
-- Name: weights fk2mi07em26rfbaipmr9a9k6rpf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weights
    ADD CONSTRAINT fk2mi07em26rfbaipmr9a9k6rpf FOREIGN KEY (tara_time) REFERENCES public.action_time(id);


--
-- TOC entry 2694 (class 2606 OID 37420)
-- Name: transportations fk2xh1qs9b27gp81px370tnqxbr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fk2xh1qs9b27gp81px370tnqxbr FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2695 (class 2606 OID 37425)
-- Name: transportations fk2yc3o26vdj0fj2qdyht4a8tng; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fk2yc3o26vdj0fj2qdyht4a8tng FOREIGN KEY (time_out) REFERENCES public.action_time(id);


--
-- TOC entry 2677 (class 2606 OID 37430)
-- Name: extraction_oil fk3c8qj02rwu2cmhg7mavr4738c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_oil
    ADD CONSTRAINT fk3c8qj02rwu2cmhg7mavr4738c FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2687 (class 2606 OID 37435)
-- Name: phones fk3f8vr9cnwj4rl0y9fe2p0j50w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phones
    ADD CONSTRAINT fk3f8vr9cnwj4rl0y9fe2p0j50w FOREIGN KEY (person) REFERENCES public.persons(id);


--
-- TOC entry 2707 (class 2606 OID 37440)
-- Name: vro_oil fk3gv0ju84pwq8g1q1iexi9sy7b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_oil
    ADD CONSTRAINT fk3gv0ju84pwq8g1q1iexi9sy7b FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2666 (class 2606 OID 37445)
-- Name: deals fk42518ajlhsjblq8mh4orfhq34; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT fk42518ajlhsjblq8mh4orfhq34 FOREIGN KEY (unit) REFERENCES public.weight_units(id);


--
-- TOC entry 2659 (class 2606 OID 37450)
-- Name: analyses_sun fk4a09dhjeyolho5i47p692rwsr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_sun
    ADD CONSTRAINT fk4a09dhjeyolho5i47p692rwsr FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2665 (class 2606 OID 37455)
-- Name: changes fk5lb47kpm715jxk9bsxpjjywnd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.changes
    ADD CONSTRAINT fk5lb47kpm715jxk9bsxpjjywnd FOREIGN KEY (log) REFERENCES public.change_log(id);


--
-- TOC entry 2680 (class 2606 OID 37460)
-- Name: extraction_raw fk6d4qc0m9hbfj6i7rmbvntwt8q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_raw
    ADD CONSTRAINT fk6d4qc0m9hbfj6i7rmbvntwt8q FOREIGN KEY (turn) REFERENCES public.turns_extraction(id);


--
-- TOC entry 2660 (class 2606 OID 37465)
-- Name: analyses_sun fk6e2ib59suwo8s5hd3qmegml72; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_sun
    ADD CONSTRAINT fk6e2ib59suwo8s5hd3qmegml72 FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2678 (class 2606 OID 37470)
-- Name: extraction_oil fk6qdqvx3b5tp4437o7vr928w17; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_oil
    ADD CONSTRAINT fk6qdqvx3b5tp4437o7vr928w17 FOREIGN KEY (turn) REFERENCES public.turns_extraction(id);


--
-- TOC entry 2662 (class 2606 OID 37475)
-- Name: bot_uids fk7d322kbek4drptnqx5dboekh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_uids
    ADD CONSTRAINT fk7d322kbek4drptnqx5dboekh FOREIGN KEY (worker) REFERENCES public.workers(id);


--
-- TOC entry 2688 (class 2606 OID 37480)
-- Name: probe_oil fk7hkoubtgxlv2rwurt2n7evo8o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_oil
    ADD CONSTRAINT fk7hkoubtgxlv2rwurt2n7evo8o FOREIGN KEY (analyses) REFERENCES public.analyses_oil(id);


--
-- TOC entry 2657 (class 2606 OID 37485)
-- Name: analyses_oil fk7mf098ysdyfuon8or1i8v7nba; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_oil
    ADD CONSTRAINT fk7mf098ysdyfuon8or1i8v7nba FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2667 (class 2606 OID 37490)
-- Name: deals fk7vhpsw2dwph2ufhyw25hk8amw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT fk7vhpsw2dwph2ufhyw25hk8amw FOREIGN KEY (visibility) REFERENCES public.document_organisations(id);


--
-- TOC entry 2674 (class 2606 OID 37495)
-- Name: extraction_crude fk8pxdtvwvnnw8i4svfi68odipg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_crude
    ADD CONSTRAINT fk8pxdtvwvnnw8i4svfi68odipg FOREIGN KEY (turn) REFERENCES public.turns_extraction(id);


--
-- TOC entry 2655 (class 2606 OID 37500)
-- Name: analyses_cake fk8t4ordakcdprtk3l2o92q2xri; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_cake
    ADD CONSTRAINT fk8t4ordakcdprtk3l2o92q2xri FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2708 (class 2606 OID 37505)
-- Name: vro_oil fk95hhnkjl0dnjqg56miu7tsanr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_oil
    ADD CONSTRAINT fk95hhnkjl0dnjqg56miu7tsanr FOREIGN KEY (turn) REFERENCES public.turns_vro(id);


--
-- TOC entry 2709 (class 2606 OID 37510)
-- Name: vro_oil fk9i36sk6gapabk5jlm6bqq5cr3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_oil
    ADD CONSTRAINT fk9i36sk6gapabk5jlm6bqq5cr3 FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2700 (class 2606 OID 37515)
-- Name: users fk9r2keh7ibi9nklqmpewwhyes5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk9r2keh7ibi9nklqmpewwhyes5 FOREIGN KEY (worker) REFERENCES public.workers(id);


--
-- TOC entry 2675 (class 2606 OID 37520)
-- Name: extraction_crude fkaslweg68ylq0d0jkctc1ko8s2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_crude
    ADD CONSTRAINT fkaslweg68ylq0d0jkctc1ko8s2 FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2704 (class 2606 OID 37525)
-- Name: vro_dailies fkatlk6889ltpnwdmlum1sfl3qq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_dailies
    ADD CONSTRAINT fkatlk6889ltpnwdmlum1sfl3qq FOREIGN KEY (turn) REFERENCES public.turns_vro(id);


--
-- TOC entry 2711 (class 2606 OID 37530)
-- Name: weights fkauod47m7gs8mvb0lvrdth3cbc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weights
    ADD CONSTRAINT fkauod47m7gs8mvb0lvrdth3cbc FOREIGN KEY (brutto_time) REFERENCES public.action_time(id);


--
-- TOC entry 2683 (class 2606 OID 37540)
-- Name: forpress_cake fkc4f9q5snwcw7hhsoj9a7kl92g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forpress_cake
    ADD CONSTRAINT fkc4f9q5snwcw7hhsoj9a7kl92g FOREIGN KEY (forpress) REFERENCES public.forpress(id);


--
-- TOC entry 2684 (class 2606 OID 37545)
-- Name: load_plans fkd0efufh9s386lbq8a9e7b7913; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.load_plans
    ADD CONSTRAINT fkd0efufh9s386lbq8a9e7b7913 FOREIGN KEY (document_organisation) REFERENCES public.document_organisations(id);


--
-- TOC entry 2690 (class 2606 OID 37550)
-- Name: products fkdcul4cpjmdfc7fluiml4n0452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkdcul4cpjmdfc7fluiml4n0452 FOREIGN KEY (product_group) REFERENCES public.product_group(id);


--
-- TOC entry 2679 (class 2606 OID 37555)
-- Name: extraction_oil fkdhtk4o95jpkqegpcmamtiv3tq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_oil
    ADD CONSTRAINT fkdhtk4o95jpkqegpcmamtiv3tq FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2663 (class 2606 OID 37560)
-- Name: bot_user_settings fke7i0g9v9uxovg1vj33xshj07o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bot_user_settings
    ADD CONSTRAINT fke7i0g9v9uxovg1vj33xshj07o FOREIGN KEY (worker) REFERENCES public.workers(id);


--
-- TOC entry 2654 (class 2606 OID 37565)
-- Name: action_time fke81u4um2nb3gceh5pa8qctoxc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.action_time
    ADD CONSTRAINT fke81u4um2nb3gceh5pa8qctoxc FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2712 (class 2606 OID 37570)
-- Name: workers fkefxcfvntfoere54u43pt2kgkb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.workers
    ADD CONSTRAINT fkefxcfvntfoere54u43pt2kgkb FOREIGN KEY (person) REFERENCES public.persons(id);


--
-- TOC entry 2681 (class 2606 OID 37575)
-- Name: extraction_raw fkfo57g0t0vb8d8wj4tryj0ddbk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_raw
    ADD CONSTRAINT fkfo57g0t0vb8d8wj4tryj0ddbk FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2671 (class 2606 OID 37580)
-- Name: drivers fkh6gclrmv9e3nd4wm3bsgaati1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT fkh6gclrmv9e3nd4wm3bsgaati1 FOREIGN KEY (vehicle) REFERENCES public.vehicles(id);


--
-- TOC entry 2703 (class 2606 OID 37585)
-- Name: vro_crude fkhglohdre0hk4a9kkl20oqg458; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_crude
    ADD CONSTRAINT fkhglohdre0hk4a9kkl20oqg458 FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2705 (class 2606 OID 37590)
-- Name: vro_dailies fkhlnxtumvbp4ixkv1pvqpt2msp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_dailies
    ADD CONSTRAINT fkhlnxtumvbp4ixkv1pvqpt2msp FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2668 (class 2606 OID 37595)
-- Name: deals fki138ngdyxomhvtvpxs54iiaji; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT fki138ngdyxomhvtvpxs54iiaji FOREIGN KEY (organisation) REFERENCES public.organisations(id);


--
-- TOC entry 2685 (class 2606 OID 37600)
-- Name: load_plans fkiro775fwsbrb392dk2s0v4ju6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.load_plans
    ADD CONSTRAINT fkiro775fwsbrb392dk2s0v4ju6 FOREIGN KEY (deal) REFERENCES public.deals(id);


--
-- TOC entry 2676 (class 2606 OID 37605)
-- Name: extraction_crude fkj33n77favw8t20xii43g6lsr1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_crude
    ADD CONSTRAINT fkj33n77favw8t20xii43g6lsr1 FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2686 (class 2606 OID 37610)
-- Name: load_plans fkj7mnyeil2kmqvnr665jncfsdu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.load_plans
    ADD CONSTRAINT fkj7mnyeil2kmqvnr665jncfsdu FOREIGN KEY (transportation) REFERENCES public.transportations(id);


--
-- TOC entry 2672 (class 2606 OID 37615)
-- Name: drivers fkjiob10wby3981io0m3d9nwhra; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT fkjiob10wby3981io0m3d9nwhra FOREIGN KEY (organisation) REFERENCES public.organisations(id);


--
-- TOC entry 2691 (class 2606 OID 37620)
-- Name: seals fkjlogglkw5awbbt0ppu05qhhmr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals
    ADD CONSTRAINT fkjlogglkw5awbbt0ppu05qhhmr FOREIGN KEY (batch) REFERENCES public.seals_batch(id);


--
-- TOC entry 2673 (class 2606 OID 37625)
-- Name: drivers fkkexepta0e34epgs43tur30jtt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT fkkexepta0e34epgs43tur30jtt FOREIGN KEY (person) REFERENCES public.persons(id);


--
-- TOC entry 2658 (class 2606 OID 37630)
-- Name: analyses_oil fkkjt5ptpqodv5apwsafrasbayn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_oil
    ADD CONSTRAINT fkkjt5ptpqodv5apwsafrasbayn FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2706 (class 2606 OID 37635)
-- Name: vro_dailies fkl9r3h4nmdd0pd0fd1d3dsxiox; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vro_dailies
    ADD CONSTRAINT fkl9r3h4nmdd0pd0fd1d3dsxiox FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2696 (class 2606 OID 37640)
-- Name: transportations fklvtm80d1sdqdlg39s43mokm2o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fklvtm80d1sdqdlg39s43mokm2o FOREIGN KEY (vehicle) REFERENCES public.vehicles(id);


--
-- TOC entry 2697 (class 2606 OID 37645)
-- Name: transportations fkn7xjeqdgqm832t97ba1my7b4s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fkn7xjeqdgqm832t97ba1my7b4s FOREIGN KEY (driver) REFERENCES public.drivers(id);


--
-- TOC entry 2682 (class 2606 OID 37650)
-- Name: extraction_raw fknhjb44qybi9lsfhyo5im9rl3n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extraction_raw
    ADD CONSTRAINT fknhjb44qybi9lsfhyo5im9rl3n FOREIGN KEY (create_time) REFERENCES public.action_time(id);


--
-- TOC entry 2692 (class 2606 OID 37655)
-- Name: seals fknj1n5eglub0c9a8rmph7emr7j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals
    ADD CONSTRAINT fknj1n5eglub0c9a8rmph7emr7j FOREIGN KEY (cargo) REFERENCES public.transportations(id);


--
-- TOC entry 2698 (class 2606 OID 37660)
-- Name: transportations fko4qx5ps3exgd5k24jlcpq0mwd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fko4qx5ps3exgd5k24jlcpq0mwd FOREIGN KEY (time_in) REFERENCES public.action_time(id);


--
-- TOC entry 2669 (class 2606 OID 37665)
-- Name: deals fkoknr6iu7c6nnulclfsba2a8ah; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT fkoknr6iu7c6nnulclfsba2a8ah FOREIGN KEY (product) REFERENCES public.products(id);


--
-- TOC entry 2693 (class 2606 OID 37670)
-- Name: seals_batch fkoqidmhxwye4nhm85jumw0ru5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seals_batch
    ADD CONSTRAINT fkoqidmhxwye4nhm85jumw0ru5 FOREIGN KEY (created) REFERENCES public.action_time(id);


--
-- TOC entry 2656 (class 2606 OID 37675)
-- Name: analyses_cake fkpqeol6mngttax1gucdpdutt47; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyses_cake
    ADD CONSTRAINT fkpqeol6mngttax1gucdpdutt47 FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2699 (class 2606 OID 37680)
-- Name: transportations fkq62baqktq6ntlsoynfsucn87; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transportations
    ADD CONSTRAINT fkq62baqktq6ntlsoynfsucn87 FOREIGN KEY (shipper) REFERENCES public.document_organisations(id);


--
-- TOC entry 2664 (class 2606 OID 37685)
-- Name: change_log fkqij04i9jkqosuxu98h8awtx5r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.change_log
    ADD CONSTRAINT fkqij04i9jkqosuxu98h8awtx5r FOREIGN KEY (creator) REFERENCES public.workers(id);


--
-- TOC entry 2689 (class 2606 OID 37690)
-- Name: probe_sun fkrmpdwdd2jw6lw47e6tbev10nc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.probe_sun
    ADD CONSTRAINT fkrmpdwdd2jw6lw47e6tbev10nc FOREIGN KEY (analyses) REFERENCES public.analyses_sun(id);


--
-- TOC entry 2670 (class 2606 OID 37700)
-- Name: deals fks3fqqb4ysty7pot6r5v0ir2vi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deals
    ADD CONSTRAINT fks3fqqb4ysty7pot6r5v0ir2vi FOREIGN KEY (creator) REFERENCES public.workers(id);


-- Completed on 2019-07-26 09:31:15

--
-- PostgreSQL database dump complete
--

