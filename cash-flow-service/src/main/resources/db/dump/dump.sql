--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

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
-- Name: assets; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA assets;


--
-- Name: budget; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA budget;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categories; Type: TABLE; Schema: budget; Owner: -
--

CREATE TABLE budget.categories (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    description character varying(255),
    CONSTRAINT categories_name_check CHECK ((length(TRIM(BOTH FROM name)) >= 2))
);


--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: budget; Owner: -
--

CREATE SEQUENCE budget.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: budget; Owner: -
--

ALTER SEQUENCE budget.categories_id_seq OWNED BY budget.categories.id;


--
-- Name: monetary_transactions; Type: TABLE; Schema: budget; Owner: -
--

CREATE TABLE budget.monetary_transactions (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    amount numeric(12,2) NOT NULL,
    categories_id integer NOT NULL,
    type_operation_id integer NOT NULL,
    date_operation timestamp without time zone NOT NULL,
    CONSTRAINT monetary_transactions_name_check CHECK ((length(TRIM(BOTH FROM name)) >= 2))
);


--
-- Name: monetary_transactions_id_seq; Type: SEQUENCE; Schema: budget; Owner: -
--

CREATE SEQUENCE budget.monetary_transactions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: monetary_transactions_id_seq; Type: SEQUENCE OWNED BY; Schema: budget; Owner: -
--

ALTER SEQUENCE budget.monetary_transactions_id_seq OWNED BY budget.monetary_transactions.id;


--
-- Name: type_operation; Type: TABLE; Schema: budget; Owner: -
--

CREATE TABLE budget.type_operation (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    description character varying(255),
    CONSTRAINT type_operation_name_check CHECK ((length(TRIM(BOTH FROM name)) >= 3))
);


--
-- Name: type_operation_id_seq; Type: SEQUENCE; Schema: budget; Owner: -
--

CREATE SEQUENCE budget.type_operation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: type_operation_id_seq; Type: SEQUENCE OWNED BY; Schema: budget; Owner: -
--

ALTER SEQUENCE budget.type_operation_id_seq OWNED BY budget.type_operation.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


--
-- Name: categories id; Type: DEFAULT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.categories ALTER COLUMN id SET DEFAULT nextval('budget.categories_id_seq'::regclass);


--
-- Name: monetary_transactions id; Type: DEFAULT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.monetary_transactions ALTER COLUMN id SET DEFAULT nextval('budget.monetary_transactions_id_seq'::regclass);


--
-- Name: type_operation id; Type: DEFAULT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.type_operation ALTER COLUMN id SET DEFAULT nextval('budget.type_operation_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: budget; Owner: -
--

INSERT INTO budget.categories VALUES (3, 'Здоровье', 'Аптеки, позод к врачам, услуги в больницах т.п.');
INSERT INTO budget.categories VALUES (4, 'Машина', 'Траты на личный автомобиль');
INSERT INTO budget.categories VALUES (1, 'Продукты ', 'Основные покупки продуктов');
INSERT INTO budget.categories VALUES (5, 'ЖКХ', 'Вся коммуналка');


--
-- Data for Name: monetary_transactions; Type: TABLE DATA; Schema: budget; Owner: -
--

INSERT INTO budget.monetary_transactions VALUES (1, 'Продукты питания', 'Покупали продукты питания на неделю', 3014.19, 1, 1, '2024-05-04 16:00:00');
INSERT INTO budget.monetary_transactions VALUES (4, 'Unknown', 'Оплата в Сервисы Яндекса', 107.00, 3, 1, '2024-06-30 16:39:00');
INSERT INTO budget.monetary_transactions VALUES (3, 'Пермэнергосбыт', 'Электричество, горячая вода, содержание и ремонт', 2981.00, 5, 1, '2024-05-11 14:54:00');
INSERT INTO budget.monetary_transactions VALUES (5, 'Unknown', 'Оплата в OTDEL ZOOTOVAROV PRIRO Berezniki RUS', 140.00, 3, 1, '2024-06-30 13:12:00');
INSERT INTO budget.monetary_transactions VALUES (6, 'Unknown', 'Оплата в LENTA-921 Berezniki RUS', 1130.99, 3, 1, '2024-06-30 13:06:00');
INSERT INTO budget.monetary_transactions VALUES (44, 'Unknown', 'Банковский перевод. УРАЛЬСКИЙ БАНК ПАО СБЕРБАНК', 32500.20, 3, 2, '2024-04-11 14:05:00');
INSERT INTO budget.monetary_transactions VALUES (2, 'Поход к дерматологу', 'Поход к дерматологу в поликлинику уралмед', 920.00, 3, 1, '2024-05-08 15:30:00');
INSERT INTO budget.monetary_transactions VALUES (7, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 239.98, 3, 1, '2024-04-30 07:57:00');
INSERT INTO budget.monetary_transactions VALUES (8, 'Unknown', 'Оплата в AIA*lukoil Respublika Ba RU', 2000.17, 3, 1, '2024-04-29 16:02:00');
INSERT INTO budget.monetary_transactions VALUES (9, 'Unknown', 'Оплата в OZON', 375.00, 3, 1, '2024-04-29 13:30:00');
INSERT INTO budget.monetary_transactions VALUES (10, 'Unknown', 'Оплата услуг mBank.MTS', 406.00, 3, 1, '2024-04-29 07:21:00');
INSERT INTO budget.monetary_transactions VALUES (11, 'Unknown', 'Оплата услуг mBank.MegaFon', 100.00, 3, 1, '2024-04-28 15:28:00');
INSERT INTO budget.monetary_transactions VALUES (12, 'Unknown', 'Оплата в MAGNIT MM VKLUCHENIE Berezniki RUS', 521.92, 3, 1, '2024-04-28 11:32:00');
INSERT INTO budget.monetary_transactions VALUES (13, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 452.46, 3, 1, '2024-04-27 19:06:00');
INSERT INTO budget.monetary_transactions VALUES (14, 'Unknown', 'Оплата в KOLESADAROM Berezniki RUS', 360.00, 3, 1, '2024-04-26 11:29:00');
INSERT INTO budget.monetary_transactions VALUES (15, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 50.00, 3, 1, '2024-04-26 10:55:00');
INSERT INTO budget.monetary_transactions VALUES (16, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 150.00, 3, 1, '2024-04-26 10:47:00');
INSERT INTO budget.monetary_transactions VALUES (17, 'Unknown', 'Оплата в M-N EVROREMONT Berezniki RUS', 185.00, 3, 1, '2024-04-26 10:06:00');
INSERT INTO budget.monetary_transactions VALUES (18, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 387.77, 3, 1, '2024-04-24 17:42:00');
INSERT INTO budget.monetary_transactions VALUES (20, 'Unknown', 'Оплата в dodopizza.ru Perm RUS', 1149.00, 3, 1, '2024-04-21 15:37:00');
INSERT INTO budget.monetary_transactions VALUES (21, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 799.82, 3, 1, '2024-04-21 13:24:00');
INSERT INTO budget.monetary_transactions VALUES (22, 'Unknown', 'Оплата услуг mBank.Rostelecom', 560.00, 3, 1, '2024-04-21 11:47:00');
INSERT INTO budget.monetary_transactions VALUES (23, 'Unknown', 'Оплата услуг mBank.ZHKH', 330.00, 3, 1, '2024-04-21 11:45:00');
INSERT INTO budget.monetary_transactions VALUES (24, 'Unknown', 'Внешний банковский перевод на счёт 40702810007240000019, НИЖЕГОРОДСКИЙ ФИЛИАЛ АБ "РОССИЯ"', 254.16, 3, 1, '2024-04-21 11:44:00');
INSERT INTO budget.monetary_transactions VALUES (25, 'Unknown', 'Оплата услуг mBank.ZHKH', 4069.71, 3, 1, '2024-04-21 11:44:00');
INSERT INTO budget.monetary_transactions VALUES (26, 'Unknown', 'Оплата в Пермэнергосбыт_SBP', 2886.88, 3, 1, '2024-04-21 11:42:00');
INSERT INTO budget.monetary_transactions VALUES (27, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 277.25, 3, 1, '2024-04-20 16:46:00');
INSERT INTO budget.monetary_transactions VALUES (28, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 150.00, 3, 1, '2024-04-16 21:39:00');
INSERT INTO budget.monetary_transactions VALUES (29, 'Unknown', 'Внешний перевод по номеру телефона +79526547055', 1900.00, 3, 1, '2024-04-14 21:19:00');
INSERT INTO budget.monetary_transactions VALUES (30, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 50.00, 3, 1, '2024-04-14 18:36:00');
INSERT INTO budget.monetary_transactions VALUES (31, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 150.00, 3, 1, '2024-04-14 18:28:00');
INSERT INTO budget.monetary_transactions VALUES (32, 'Unknown', 'Оплата в MD.*IP Berezij Berezniki RUS', 100.00, 3, 1, '2024-04-14 17:55:00');
INSERT INTO budget.monetary_transactions VALUES (33, 'Unknown', 'Оплата в AIA*benzuber g. Moskva RU', 1499.49, 3, 1, '2024-04-14 11:17:00');
INSERT INTO budget.monetary_transactions VALUES (34, 'Unknown', 'Внутрибанковский перевод с договора 5503400375', 1000.00, 3, 1, '2024-04-14 11:12:00');
INSERT INTO budget.monetary_transactions VALUES (35, 'Unknown', 'Оплата в MAGAZIN Berezniki RUS', 1585.28, 3, 1, '2024-04-14 11:07:00');
INSERT INTO budget.monetary_transactions VALUES (36, 'Unknown', 'Оплата в PYATEROCHKA 2666 Berezniki RUS', 454.88, 3, 1, '2024-04-14 11:03:00');
INSERT INTO budget.monetary_transactions VALUES (37, 'Unknown', 'Оплата в LENTA-921 Berezniki RUS', 1965.36, 3, 1, '2024-04-14 10:52:00');
INSERT INTO budget.monetary_transactions VALUES (38, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 561.42, 3, 1, '2024-04-13 19:05:00');
INSERT INTO budget.monetary_transactions VALUES (39, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 410.56, 3, 1, '2024-04-12 20:18:00');
INSERT INTO budget.monetary_transactions VALUES (40, 'Unknown', 'Оплата в TRANSPORT PERM KRAI Perm RUS', 30.00, 3, 1, '2024-04-12 17:43:00');
INSERT INTO budget.monetary_transactions VALUES (41, 'Unknown', 'Оплата в VEESP Sankt- Peterbu RUS', 500.00, 3, 1, '2024-04-11 16:14:00');
INSERT INTO budget.monetary_transactions VALUES (42, 'Unknown', 'Пополнение. Система быстрых платежей', 110.00, 3, 1, '2024-04-11 15:37:00');
INSERT INTO budget.monetary_transactions VALUES (43, 'Unknown', 'Пополнение. Система быстрых платежей', 110.00, 3, 1, '2024-04-11 15:01:00');
INSERT INTO budget.monetary_transactions VALUES (45, 'Unknown', 'Пополнение. Система быстрых платежей', 70.00, 3, 1, '2024-04-11 13:03:00');
INSERT INTO budget.monetary_transactions VALUES (46, 'Unknown', 'Внутрибанковский перевод с договора 5691936685', 55.00, 3, 1, '2024-04-11 12:40:00');
INSERT INTO budget.monetary_transactions VALUES (47, 'Unknown', 'Оплата в TRANSPORT PERM KRAI Perm RUS', 30.00, 3, 1, '2024-04-08 17:26:00');
INSERT INTO budget.monetary_transactions VALUES (48, 'Unknown', 'Отмена операции оплаты LENTA-921 Berezniki RUS', 952.34, 3, 1, '2024-04-08 17:05:00');
INSERT INTO budget.monetary_transactions VALUES (49, 'Unknown', 'Внутренний перевод на договор 8101173778', 2370.00, 3, 1, '2024-04-07 11:40:00');
INSERT INTO budget.monetary_transactions VALUES (50, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 214.98, 3, 1, '2024-04-07 11:27:00');
INSERT INTO budget.monetary_transactions VALUES (51, 'Unknown', 'Оплата в KOLESADAROM Berezniki RUS', 9630.00, 3, 1, '2024-04-07 10:54:00');
INSERT INTO budget.monetary_transactions VALUES (52, 'Unknown', 'Внутрибанковский перевод с договора 8101173778', 12000.00, 3, 1, '2024-04-07 10:25:00');
INSERT INTO budget.monetary_transactions VALUES (53, 'Unknown', 'Оплата в LAVKA MYASNIKA Berezniki RUS', 820.50, 3, 1, '2024-04-06 14:43:00');
INSERT INTO budget.monetary_transactions VALUES (54, 'Unknown', 'Оплата в MONETKA Berezniki RUS', 113.56, 3, 1, '2024-04-06 11:11:00');
INSERT INTO budget.monetary_transactions VALUES (55, 'Unknown', 'Оплата в MAGAZIN Berezniki RUS', 539.08, 3, 1, '2024-04-06 10:44:00');
INSERT INTO budget.monetary_transactions VALUES (56, 'Unknown', 'Оплата в LENTA-921 Berezniki RUS', 2239.47, 3, 1, '2024-04-06 10:34:00');
INSERT INTO budget.monetary_transactions VALUES (57, 'Unknown', 'Кэшбэк за обычные покупки', 520.31, 3, 1, '2024-04-05 22:46:00');
INSERT INTO budget.monetary_transactions VALUES (58, 'Unknown', 'Кэшбэк за покупки в Городе', 68.69, 3, 1, '2024-04-05 22:38:00');
INSERT INTO budget.monetary_transactions VALUES (59, 'Unknown', 'Оплата в SHAURMA Berezniki RUS', 500.00, 3, 1, '2024-04-05 18:35:00');
INSERT INTO budget.monetary_transactions VALUES (60, 'Unknown', 'Оплата в KULINARIYA S PYLU S ZH Berezniki RUS', 148.00, 3, 1, '2024-04-05 16:05:00');
INSERT INTO budget.monetary_transactions VALUES (61, 'Unknown', 'Оплата в Сервисы Яндекса', 230.00, 3, 1, '2024-04-03 20:25:00');
INSERT INTO budget.monetary_transactions VALUES (62, 'Unknown', 'Оплата в MONETKA Berezniki RUS', 36.99, 3, 1, '2024-04-03 17:45:00');
INSERT INTO budget.monetary_transactions VALUES (19, 'Unknown', 'Банковский перевод. УРАЛЬСКИЙ БАНК ПАО СБЕРБАНК', 32500.00, 3, 2, '2024-04-24 14:10:00');
INSERT INTO budget.monetary_transactions VALUES (63, 'Unknown', 'Оплата в TRANSPORT PERM KRAI Perm RUS', 30.00, 3, 1, '2024-04-03 17:37:00');
INSERT INTO budget.monetary_transactions VALUES (64, 'Unknown', 'Оплата услуг mBank.MegaFon', 100.00, 3, 1, '2024-04-02 19:05:00');
INSERT INTO budget.monetary_transactions VALUES (65, 'Unknown', 'Оплата в PYATEROCHKA 2646 Berezniki RUS', 262.98, 3, 1, '2024-04-01 10:24:00');


--
-- Data for Name: type_operation; Type: TABLE DATA; Schema: budget; Owner: -
--

INSERT INTO budget.type_operation VALUES (1, 'Расходы', 'Основные расходы');
INSERT INTO budget.type_operation VALUES (2, 'Доходы', 'Тип операций для доходов(ЗП, % по вкладам, акции и т.п.)');


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.flyway_schema_history VALUES (0, NULL, '<< Flyway Schema Creation >>', 'SCHEMA', '"assets"', NULL, 'budget', '2024-05-02 19:52:06.487273', 0, true);
INSERT INTO public.flyway_schema_history VALUES (1, '1.1.1', 'Basic schema', 'SQL', 'V1_1_1__Basic_schema.sql', 939808925, 'budget', '2024-05-02 19:52:06.508214', 23, true);


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: budget; Owner: -
--

SELECT pg_catalog.setval('budget.categories_id_seq', 5, true);


--
-- Name: monetary_transactions_id_seq; Type: SEQUENCE SET; Schema: budget; Owner: -
--

SELECT pg_catalog.setval('budget.monetary_transactions_id_seq', 65, true);


--
-- Name: type_operation_id_seq; Type: SEQUENCE SET; Schema: budget; Owner: -
--

SELECT pg_catalog.setval('budget.type_operation_id_seq', 2, true);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: monetary_transactions monetary_transactions_pkey; Type: CONSTRAINT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.monetary_transactions
    ADD CONSTRAINT monetary_transactions_pkey PRIMARY KEY (id);


--
-- Name: type_operation type_operation_pkey; Type: CONSTRAINT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.type_operation
    ADD CONSTRAINT type_operation_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: monetary_transactions monetary_transactions_categories_id_fkey; Type: FK CONSTRAINT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.monetary_transactions
    ADD CONSTRAINT monetary_transactions_categories_id_fkey FOREIGN KEY (categories_id) REFERENCES budget.categories(id);


--
-- Name: monetary_transactions monetary_transactions_type_operation_id_fkey; Type: FK CONSTRAINT; Schema: budget; Owner: -
--

ALTER TABLE ONLY budget.monetary_transactions
    ADD CONSTRAINT monetary_transactions_type_operation_id_fkey FOREIGN KEY (type_operation_id) REFERENCES budget.type_operation(id);


--
-- PostgreSQL database dump complete
--

