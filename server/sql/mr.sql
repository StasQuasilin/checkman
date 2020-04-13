CREATE TABLE public.manufacture_reports
(
  id SERIAL PRIMARY KEY NOT NULL,
  turn INT NOT NULL,
  creator INT NOT NULL
);
ALTER TABLE public.manufacture_reports
ADD CONSTRAINT unique_id UNIQUE (id);

CREATE TABLE public.report_fields
(
  id SERIAL PRIMARY KEY NOT NULL,
  category INT NOT NULL,
  roundReport INT NOT NULL,
  title VARCHAR(45) NOT NULL,
  storage INT,
  value REAL DEFAULT 0 NOT NULL,
  unit INT NOT NULL,
  comment VARCHAR(255)
);
ALTER TABLE public.report_fields
ADD CONSTRAINT unique_id UNIQUE (id);

CREATE TABLE public.report_field_settings
(
  id SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(45) NOT NULL,
  unit INT NOT NULL,
  category INT,
  storage INT
);
ALTER TABLE public.report_field_settings
ADD CONSTRAINT unique_id UNIQUE (id);

CREATE TABLE public.report_field_category
(
  id SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(45) NOT NULL,
  number INT DEFAULT 0 NOT NULL
);
ALTER TABLE public.report_field_category
ADD CONSTRAINT unique_id UNIQUE (id);