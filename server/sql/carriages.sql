CREATE SEQUENCE carriages_id_seq START 1 INCREMENT 1 CACHE 1;
CREATE TABLE public.carriages (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('carriages_id_seq'::regclass),
  deal INTEGER NOT NULL,
  number CHARACTER VARYING(12) NOT NULL
);
CREATE SEQUENCE carriage_load_plans_id_seq START 1 INCREMENT 1 CACHE 1;
CREATE TABLE public.carriage_load_plans (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('carriage_load_plans_id_seq'::regclass),
  carriage INTEGER NOT NULL,
  load_plan INTEGER NOT NULL
);
CREATE SEQUENCE carriage_analyses_id_seq START 1 INCREMENT 1 CACHE 1;
CREATE TABLE public.carriage_analyses (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('carriage_analyses_id_seq'::regclass),
  carriage INTEGER NOT NULL,
  oil INTEGER NOT NULL,
  meal INTEGER NOT NULL
);