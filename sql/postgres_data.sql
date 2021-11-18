--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1 (Debian 14.1-1.pgdg110+1)
-- Dumped by pg_dump version 14.1 (Debian 14.1-1.pgdg110+1)

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

DROP DATABASE validation_source;
--
-- Name: validation_source; Type: DATABASE; Schema: -; Owner: data_validation_user
--

CREATE DATABASE validation_source WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE validation_source OWNER TO data_validation_user;

\connect validation_source

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
-- Name: employees_source; Type: TABLE; Schema: public; Owner: data_validation_user
--

CREATE TABLE public.employees_source (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE public.employees_source OWNER TO data_validation_user;

--
-- Data for Name: employees_source; Type: TABLE DATA; Schema: public; Owner: data_validation_user
--

COPY public.employees_source (id, name, email) FROM stdin;
1	Dibakar Dutta	dibakard@emids.com
2	Manikandan Elumalai	manikandane@emids.com
\.


--
-- Name: employees_source employees_source_pkey; Type: CONSTRAINT; Schema: public; Owner: data_validation_user
--

ALTER TABLE ONLY public.employees_source
    ADD CONSTRAINT employees_source_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

