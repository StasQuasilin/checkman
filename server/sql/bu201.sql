
--
-- TOC entry 2851 (class 0 OID 17430)
-- Dependencies: 254
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (53,	'Володимир',	'Петрович',	'Дяченко');
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (114,	'Н.', 'А.',	'Купин');
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (80,	'н',	'и',	'Каруник');
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (112,	NULL, NULL,	'Папр');
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (134,	'А.', 'В.',	'Михайлик');
INSERT INTO public.persons (id, forename, patronymic, surname) VALUES (149,	'Николай',	'Иванович',	'Лопа');

INSERT INTO public.action_time (id, "time", creator) VALUES (2,	'2019-07-05 16:29:30.495', 1);
INSERT INTO public.action_time (id, "time", creator) VALUES (5,	'2019-07-05 16:30:09.37',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (9,	'2019-07-05 16:30:32.193',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (11, '2019-07-05 16:30:48.667',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (13,	'2019-07-05 16:31:01.911',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (15,	'2019-07-05 16:31:14.579',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (17,	'2019-07-05 16:31:26.903',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (19,	'2019-07-05 16:31:36.044',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (21,	'2019-07-05 16:31:46.449',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (24,	'2019-07-05 16:32:09.491',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (27,	'2019-07-05 16:33:00.019',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (30,	'2019-07-05 16:33:10.471',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (32,	'2019-07-05 16:33:26.867',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (57,	'2019-07-05 16:35:15.1',	3);
INSERT INTO public.action_time (id, "time", creator) VALUES (60,	'2019-07-05 16:36:15.347',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (63,	'2019-07-05 16:45:33.61',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (64,	'2019-07-05 16:45:33.61',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (70,	'2019-07-05 16:46:39.286',	1);
INSERT INTO public.action_time (id, "time", creator) VALUES (96,	'2019-07-10 13:50:05.663',	2);
INSERT INTO public.action_time (id, "time", creator) VALUES (99,	'2019-07-10 13:50:37.191',	2);
INSERT INTO public.action_time (id, "time", creator) VALUES (100,	'2019-07-10 13:50:37.191',	2);
INSERT INTO public.action_time (id, "time", creator) VALUES (105,	'2019-07-10 13:50:54.46',	2);



INSERT INTO public.organisations (id, name, type) VALUES (1,	'Сумський ЗПТ',	'ПрАТ');
INSERT INTO public.organisations (id, name, type) VALUES (36,	'АЛЬЖЕ',	'ТОВ');
INSERT INTO public.organisations (id, name, type) VALUES (74,	'ОЛИЯР',	'ПП');
INSERT INTO public.organisations (id, name, type) VALUES (109,	'ОТРОХ',	'ПП');
INSERT INTO public.organisations (id, name, type) VALUES (116,	'САНОЛТА',	'ТОВ');

INSERT INTO public.app_settings (id, customer, organisation) VALUES (1,	'szpt',	1);

INSERT  INTO public.bot_uids (id, term, uid, worker) VALUES (73,	'2019-07-08 08:42:45.173',	'aGpZVVdA',	1);


INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (38,	'de92c4e5-f9f0-436a-8ef1-1bdf94a71f6e',	'new',	'2019-07-05 16:33:51.889',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (44,	'a4bb99f0-9310-40e6-8c93-ac489f2a93ba',	'new.plan',	'2019-07-05 16:34:02.825',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (51,	'cbe5797f-fa17-4f68-b8e0-41de9dc49c7e',	'transportation.edit',	'2019-07-05 16:34:18.191',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (55,	'cbe5797f-fa17-4f68-b8e0-41de9dc49c7e',	'transportation.edit',	'2019-07-05 16:34:39.142',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (66,	'a1c649a4-cd74-4294-aa0c-f230ff6bf79d',	'weight.edit',	'2019-07-05 16:45:33.625',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (71,	'cbe5797f-fa17-4f68-b8e0-41de9dc49c7e',	'transportation.edit',	'2019-07-05 16:46:39.286',	1);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (76,	'dd73e15d-86c1-42fa-8d00-ab47f7e9dcef',	'new',	'2019-07-10 13:41:16.682',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (86,	'c828d766-cd35-4aff-8f55-a3146e97fc81',	'new.plan',	'2019-07-10 13:44:36.035',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (92,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.new',	'2019-07-10 13:44:36.05',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (97,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-10 13:50:05.694',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (102,	'e736890b-a7c0-4695-a382-1666c85e95dc',	'weight.edit',	'2019-07-10 13:50:37.191',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (106,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-10 13:50:54.476',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (118,	'd89786a0-8662-4c2c-baf5-1dea8e232c32',	'new',	'2019-07-10 13:56:24.151',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (125,	'cb5f423a-c588-4a61-94f5-6eb42a763a2b',	'new.plan',	'2019-07-10 13:57:29.468',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (131,	'55ca8e9c-32fa-4bd1-b19b-45e860635b78',	'transportation.new',	'2019-07-10 13:57:29.484',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (138,	'55ca8e9c-32fa-4bd1-b19b-45e860635b78',	'transportation.edit',	'2019-07-10 13:58:54.177',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (141,	'55ca8e9c-32fa-4bd1-b19b-45e860635b78',	'transportation.edit',	'2019-07-10 13:59:01.992',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (144,	'55ca8e9c-32fa-4bd1-b19b-45e860635b78',	'transportation.edit',	'2019-07-10 13:59:52.037',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (151,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-12 09:11:23.781',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (154,	'c828d766-cd35-4aff-8f55-a3146e97fc81',	'edit.plan',	'2019-07-12 09:11:50.613',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (156,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-12 09:11:50.613',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (158,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-12 09:12:27.835',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (162,	'4ec0413a-631a-439c-9582-c80b7054880c',	'new.plan',	'2019-07-12 09:12:27.851',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (168,	'076b6c4b-404f-4acf-a109-0f0af9760ff8',	'transportation.new',	'2019-07-12 09:12:27.866',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (173,	'e9e63f23-9270-4928-8122-f8897577ce8f',	'transportation.edit',	'2019-07-12 09:13:13.949',	2);

INSERT INTO public.change_log (id, document, label, "time", creator)
VALUES (175,	'076b6c4b-404f-4acf-a109-0f0af9760ff8',	'transportation.edit',	'2019-07-12 09:13:13.964',	2);




--
-- TOC entry 2812 (class 0 OID 17304)
-- Dependencies: 215
-- Data for Name: changes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.changes (id, field, new_value, old_value, log) FROM stdin;
39	date	2019-07-05	NULL	38
40	dateTo	2019-07-05	NULL	38
41	from	АЛЬЖЕ, ТОВ	NULL	38
45	plan.date	05.07.19	NULL	44
46	plan.deal	37	NULL	44
47	plan.plan	22.0	NULL	44
48	plan.customer	szpt	NULL	44
49	plan.from	СЗПТ	NULL	44
52	transportation.vehicle	ДАФ 'АМ 58-96 АА''АВ 55-98 АА'	NULL	51
56	transportation.driver	Дяченко В. П.	NULL	55
67	weight.brutto	38.5	0.0	66
68	weight.tara	16.22	0.0	66
72	transportation.in	05.07.19, 16:46	NULL	71
77	date	2019-07-15	NULL	76
78	dateTo	2019-07-21	NULL	76
79	from	ОЛИЯР, ПП	NULL	76
87	plan.date	15.07.19	NULL	86
88	plan.deal	75	NULL	86
89	plan.plan	25.0	NULL	86
90	plan.customer	szpt	NULL	86
91	plan.from	СЗПТ	NULL	86
93	transportation.vehicle	 'ВМ 47-55 АП'''	NULL	92
94	transportation.driver	Каруник н. и.	NULL	92
98	transportation.in	10.07.19, 13:50	NULL	97
103	weight.brutto	40.5	0.0	102
104	weight.tara	13.3	0.0	102
107	transportation.out	10.07.19, 13:50	NULL	106
108	transportation.archive.in	.in	.out	106
119	date	2019-07-15	NULL	118
120	dateTo	2019-07-21	NULL	118
121	from	САНОЛТА, ТОВ	NULL	118
126	plan.date	15.07.19	NULL	125
127	plan.deal	117	NULL	125
128	plan.plan	25.0	NULL	125
129	plan.customer	szpt	NULL	125
130	plan.from	СЗПТ	NULL	125
132	transportation.vehicle	 'АХ 56-89 УВ'''	 'ВМ 47-55 АП'''	131
133	transportation.driver	NULL	Каруник н. и.	131
139	transportation.vehicle	NULL	 'АХ 56-89 УВ'''	138
140	transportation.driver	Михайлик А.	Каруник н. и.	138
142	transportation.vehicle	NULL	 'АХ 56-89 УВ'''	141
145	transportation.vehicle	NULL	 'ВМ 47-55 АП'''	144
152	transportation.vehicle	NULL	 'ВМ 47-55 АП'''	151
153	transportation.driver	Лопа Н. И.	Каруник н. и.	151
155	plan.date	22.07.19	15.07.19	154
157	transportation.vehicle	NULL	 'ВМ 47-55 АП'''	156
159	transportation.vehicle	NULL	 'ВМ 47-55 АП'''	158
163	plan.date	12.07.19	NULL	162
164	plan.deal	75	NULL	162
165	plan.plan	25.0	NULL	162
166	plan.customer	szpt	NULL	162
167	plan.from	СЗПТ	NULL	162
169	transportation.vehicle	ДАФ 'АМ 58-96 АА''АВ 55-98 АА'	 'ВМ 47-55 АП'''	168
170	transportation.driver	Лопа М.	Лопа Н. И.	168
174	transportation.vehicle	NULL	 'ВМ 47-55 АП'''	173
176	transportation.vehicle	 'АХ 76-54 ВК'''	ДАФ 'АМ 58-96 АА''АВ 55-98 АА'	175
177	transportation.driver	Лопа М.	Лопа М.	175
\.

--
-- TOC entry 2814 (class 0 OID 17312)
-- Dependencies: 217
-- Data for Name: deals; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.deals
(id, archive, date, date_to, complete, price, quantity, type, uid, creator, visibility, organisation, product, unit)
VALUES (
  117,	'2019-07-10',	'2019-07-21',	0,	6309,	150,	'sell',	'd89786a0-8662-4c2c-baf5-1dea8e232c32',	2,	1,	116,	5,	1
);
INSERT INTO public.deals
(id, archive, date, date_to, complete, price, quantity, type, uid, creator, visibility, organisation, product, unit)
VALUES (
  75,	'2019-07-10',	'2019-07-22',	27.28,	21000,	200,	'sell',	'dd73e15d-86c1-42fa-8d00-ab47f7e9dcef',	2,	1,	74,	2,	1
);
INSERT INTO public.deals
(id, archive, date, date_to, complete, price, quantity, type, uid, creator, visibility, organisation, product, unit)
VALUES (
  37,	'2019-07-05',	'2019-07-12',	0,	9850,	22,	'buy',	'de92c4e5-f9f0-436a-8ef1-1bdf94a71f6e',	1,	1,	36,	1,	1
);



INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  50, 'ДАФ',	'АМ 58-96 АА',	'АВ 55-98 АА', NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  82,	'ДАФ',	'ВМ 14-58 ОО',	'АА 45-85', 	NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  83,	NULL,	'ВМ 47-55 АП', NULL, NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  110, NULL,	'АХ 76-54 ВК', NULL, NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  111,	NULL,	'АХ 76-54 ВК', NULL, NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  122,	NULL,	'АХ 56-89 УВ', NULL, NULL
);
INSERT INTO public.vehicles (id, model, number, trailer, transporter) VALUES (
  146,	NULL,	'DV 458-56 JK',	'GH 45-86 JH', NULL
);




INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  54,	NULL,	NULL,	53,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  81,	NULL,	NULL,	80,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  113,	NULL,	NULL,	112,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  115,	NULL,	NULL,	114,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  135,	NULL,	NULL,	134,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  137,	NULL,	NULL,	136,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  148,	NULL,	NULL,	147,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  150,	NULL,	NULL,	149,	NULL
);
INSERT INTO public.drivers (id, license, organisation, person, vehicle) VALUES (
  172,	NULL,	NULL,	171,	NULL
);



\.


--
-- TOC entry 2892 (class 0 OID 17546)
-- Dependencies: 295
-- Data for Name: turns_extraction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.turns_extraction (id, turn) VALUES (8, 1);
8	1
\.


--
-- TOC entry 2819 (class 0 OID 17329)
-- Dependencies: 222
-- Data for Name: extraction_crude; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.extraction_crude (id, dissolvent, fraction, grease, humidity, humidity_income, miscellas, "time", create_time, creator, turn) VALUES (
  10,	0.000699999975,	0.0799999982,	0.0799999982,	0.0799999982,	0.400000006,	0.0700000003,	'2019-07-05 16:30:00',	9,	1,	8
);
INSERT INTO public.extraction_crude (id, dissolvent, fraction, grease, humidity, humidity_income, miscellas, "time", create_time, creator, turn) VALUES (
  14,	0.000899999985,	0.100000001,	0.0799999982,	0.0900000036,	0.899999976,	0.0900000036,	'2019-07-05 18:30:00',	13,	1,	8
);

\.


--
-- TOC entry 2821 (class 0 OID 17334)
-- Dependencies: 224
-- Data for Name: extraction_oil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_oil (id, acid, explosion_t, humidity, peroxide, phosphorus, create_time, creator, turn) FROM stdin;
22	0.0799999982	0.0799999982	0.109999999	0.0799999982	0.0599999987	21	1	8
\.


--
-- TOC entry 2823 (class 0 OID 17339)
-- Dependencies: 226
-- Data for Name: extraction_raw; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_raw (id, cellulose, protein, "time", create_time, creator, turn) FROM stdin;
\.


--
-- TOC entry 2825 (class 0 OID 17344)
-- Dependencies: 228
-- Data for Name: extraction_storage_grease; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_storage_grease (id, turn, "time", storage, grease, humidity, create_time, creator) FROM stdin;
18	8	2019-07-05 18:30:00	1	0.800000012	1.20000005	17	1
\.


--
-- TOC entry 2827 (class 0 OID 17350)
-- Dependencies: 230
-- Data for Name: extraction_storage_protein; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_storage_protein (id, turn, "time", storage, protein, humidity, create_time, creator) FROM stdin;
12	8	2019-07-05 18:30:00	1	0.800000012	1.10000002	11	1
\.


--
-- TOC entry 2829 (class 0 OID 17357)
-- Dependencies: 232
-- Data for Name: extraction_turn_grease; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_turn_grease (id, turn, grease, humidity, create_time, creator) FROM stdin;
20	8	0.109999999	0.109999999	19	1
\.


--
-- TOC entry 2831 (class 0 OID 17364)
-- Dependencies: 234
-- Data for Name: extraction_turn_protein; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extraction_turn_protein (id, turn, humidity, create_time, creator, protein) FROM stdin;
16	8	0.0599999987	15	1	0.0700000003
\.


--
-- TOC entry 2833 (class 0 OID 17371)
-- Dependencies: 236
-- Data for Name: forpress; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forpress (id, name) FROM stdin;
1	Ф-300
2	Ф-100
\.


--
-- TOC entry 2834 (class 0 OID 17374)
-- Dependencies: 237
-- Data for Name: forpress_cake; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forpress_cake (id, humidity, oiliness, crude, forpress) FROM stdin;
26	0.0599999987	0.100000001	25	1
29	0.0900000036	0.100000001	28	1
\.


--
-- TOC entry 2837 (class 0 OID 17381)
-- Dependencies: 240
-- Data for Name: kpo_parts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kpo_parts (id, date, organoleptic, color, acid, peroxide, soap, create_time, creator, number) FROM stdin;
\.


--
-- TOC entry 2883 (class 0 OID 17523)
-- Dependencies: 286
-- Data for Name: transportations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.transportations
(id, archive, uid, creator, shipper, driver, time_in, time_out,
 vehicle, weight, sun_analyses, oil_analyses,
 meal_analyses, date, counterparty, type, product) VALUES (
  123,	FALSE,	'55ca8e9c-32fa-4bd1-b19b-45e860635b78',	2,	1,	113,	NULL,	NULL,
  NULL,	143, NULL,	NULL,	NULL,	NULL, NULL, NULL, NULL
);
INSERT INTO public.transportations
(id, archive, uid, creator, shipper, driver, time_in, time_out,
 vehicle, weight, sun_analyses, oil_analyses,
 meal_analyses, date, counterparty, type, product) VALUES (
  84,	TRUE,	'e9e63f23-9270-4928-8122-f8897577ce8f',	2,	1,	150,	96,	105,	NULL,	101,	NULL,	NULL,	NULL,	NULL
  ,	NULL,	NULL,	NULL
);
INSERT INTO public.transportations
(id, archive, uid, creator, shipper, driver, time_in, time_out,
 vehicle, weight, sun_analyses, oil_analyses,
 meal_analyses, date, counterparty, type, product) VALUES (
  160,	FALSE,	'076b6c4b-404f-4acf-a109-0f0af9760ff8',	2,	1,	172,	NULL,	NULL,	110,	NULL,	NULL,	NULL,	NULL,	NULL
  ,	NULL,	NULL,	NULL
);
INSERT INTO public.transportations
(id, archive, uid, creator, shipper, driver, time_in, time_out,
 vehicle, weight, sun_analyses, oil_analyses,
 meal_analyses, date, counterparty, type, product) VALUES (
  42,	FALSE,	'cbe5797f-fa17-4f68-b8e0-41de9dc49c7e',	1,	1,	54,	70,	NULL,	50,	65,	58,	NULL,	NULL,	NULL
  ,	NULL,	NULL,	NULL
);


INSERT INTO public.load_plans (id, canceled, transport_customer, date, plan, uid, deal, document_organisation, transportation) VALUES (
  124,	FALSE,	'szpt',	'2019-07-15',	25,	'cb5f423a-c588-4a61-94f5-6eb42a763a2b',	117,	1,	123
);
INSERT INTO public.load_plans (id, canceled, transport_customer, date, plan, uid, deal, document_organisation, transportation) VALUES (
  85,	FALSE,	'szpt',	'2019-07-22',	25,	'c828d766-cd35-4aff-8f55-a3146e97fc81',	75,	1,	84
);
INSERT INTO public.load_plans (id, canceled, transport_customer, date, plan, uid, deal, document_organisation, transportation) VALUES (
  161,	FALSE,	'szpt',	'2019-07-12',	25,	'4ec0413a-631a-439c-9582-c80b7054880c',	75,	1,	160
);
INSERT INTO public.load_plans (id, canceled, transport_customer, date, plan, uid, deal, document_organisation, transportation) VALUES (
  43,	FALSE,	'szpt',	'2019-07-05',	22,	'a4bb99f0-9310-40e6-8c93-ac489f2a93ba',	37,	1,	42
);


\.


--
-- TOC entry 2843 (class 0 OID 17401)
-- Dependencies: 246
-- Data for Name: oil_mass_fractions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oil_mass_fractions (id, turn, seed, seed_humidity, husk, husk_humidity, create_time, creator) FROM stdin;
\.


--
-- TOC entry 2845 (class 0 OID 17410)
-- Dependencies: 248
-- Data for Name: oil_mass_fractions_dry; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.oil_mass_fractions_dry (id, turn, seed, husk, create_time, creator) FROM stdin;
\.


--
-- TOC entry 2847 (class 0 OID 17417)
-- Dependencies: 250
-- Data for Name: organisation_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organisation_types (id, name) FROM stdin;
1	ПП
2	ТОВ
3	ПАТ
4	ПРАТ
5	ФОП
6	ФГ
7	СФГ
8	ПСП
\.


--
-- TOC entry 2853 (class 0 OID 17438)
-- Dependencies: 256
-- Data for Name: phones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.phones (id, number, person) FROM stdin;
\.


--
-- TOC entry 2855 (class 0 OID 17443)
-- Dependencies: 258
-- Data for Name: probe_oil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.probe_oil (id, organisation, analyses, turn, manager) FROM stdin;
7	Оліяр	6	1	Гончаренко
\.


--
-- TOC entry 2857 (class 0 OID 17448)
-- Dependencies: 260
-- Data for Name: probe_oilcake; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.probe_oilcake (id, turn, organisation, analyses, manager) FROM stdin;
\.


--
-- TOC entry 2859 (class 0 OID 17453)
-- Dependencies: 262
-- Data for Name: probe_sun; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.probe_sun (id, organisation, analyses, turn, manager) FROM stdin;
4	Альже ТОВ	3	1	Барабаш
62	АЛЬЖЕ, ТОВ	61	1	барабаш
\.


--
-- TOC entry 2862 (class 0 OID 17461)
-- Dependencies: 265
-- Data for Name: product_properties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product_properties (id, product, key, value) FROM stdin;
\.


--
-- TOC entry 2867 (class 0 OID 17477)
-- Dependencies: 270
-- Data for Name: seals_batch; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seals_batch (id, created, archive, free, title, total) FROM stdin;
\.


--
-- TOC entry 2866 (class 0 OID 17474)
-- Dependencies: 269
-- Data for Name: seals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seals (id, number, batch, transportation) FROM stdin;
\.


--
-- TOC entry 2870 (class 0 OID 17487)
-- Dependencies: 273
-- Data for Name: storage_analyses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storage_analyses (id, turn, storage, oil) FROM stdin;
35	34	2	33
\.


--
-- TOC entry 2872 (class 0 OID 17492)
-- Dependencies: 275
-- Data for Name: storage_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storage_product (id, storage, product) FROM stdin;
1	1	5
2	2	2
3	3	2
4	4	2
5	5	2
6	6	2
7	7	2
\.


--
-- TOC entry 2874 (class 0 OID 17497)
-- Dependencies: 277
-- Data for Name: storage_turns; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storage_turns (id, turn) FROM stdin;
34	1
\.


--
-- TOC entry 2876 (class 0 OID 17502)
-- Dependencies: 279
-- Data for Name: storages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storages (id, name) FROM stdin;
1	Склад Шроту
2	Ємність 150 м2 №1
3	Ємність 150 м2 №2
4	Ємність 150 м3 №3
5	Свічка №1
6	Свічка №2
7	Свічка №3
\.


--
-- TOC entry 2878 (class 0 OID 17507)
-- Dependencies: 281
-- Data for Name: subdivisions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subdivisions (id, key, name) FROM stdin;
1	ex	Екстракція
2	vro	ВРО
3	kpo	КПО
\.


--
-- TOC entry 2879 (class 0 OID 17513)
-- Dependencies: 282
-- Data for Name: trains; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trains (id, deal) FROM stdin;
\.


--
-- TOC entry 2881 (class 0 OID 17518)
-- Dependencies: 284
-- Data for Name: transportation_notes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transportation_notes (id, transportation, date, note, creator) FROM stdin;
\.


--
-- TOC entry 2885 (class 0 OID 17528)
-- Dependencies: 288
-- Data for Name: truck_transportation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.truck_transportation (id, truck, transportation) FROM stdin;
\.


--
-- TOC entry 2888 (class 0 OID 17535)
-- Dependencies: 291
-- Data for Name: trucks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trucks (id, train, number) FROM stdin;
\.


--
-- TOC entry 2890 (class 0 OID 17540)
-- Dependencies: 293
-- Data for Name: turn_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turn_settings (id, _begin, _end, number) FROM stdin;
1	08:00:00	20:00:00	1
2	20:00:00	08:00:00	2
\.


--
-- TOC entry 2891 (class 0 OID 17543)
-- Dependencies: 294
-- Data for Name: turns; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turns (id, date, number) FROM stdin;
1	2019-07-05 08:00:00	1
\.


--
-- TOC entry 2839 (class 0 OID 17388)
-- Dependencies: 242
-- Data for Name: turns_laboratory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turns_laboratory (id, turn, worker) FROM stdin;
\.


--
-- TOC entry 2895 (class 0 OID 17553)
-- Dependencies: 298
-- Data for Name: turns_vro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turns_vro (id, turn) FROM stdin;
23	1
\.


--
-- TOC entry 2897 (class 0 OID 17558)
-- Dependencies: 300
-- Data for Name: uid; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.uid (uid) FROM stdin;
de92c4e5-f9f0-436a-8ef1-1bdf94a71f6e
a4bb99f0-9310-40e6-8c93-ac489f2a93ba
cbe5797f-fa17-4f68-b8e0-41de9dc49c7e
a1c649a4-cd74-4294-aa0c-f230ff6bf79d
dd73e15d-86c1-42fa-8d00-ab47f7e9dcef
c828d766-cd35-4aff-8f55-a3146e97fc81
e9e63f23-9270-4928-8122-f8897577ce8f
e736890b-a7c0-4695-a382-1666c85e95dc
d89786a0-8662-4c2c-baf5-1dea8e232c32
cb5f423a-c588-4a61-94f5-6eb42a763a2b
55ca8e9c-32fa-4bd1-b19b-45e860635b78
98f552dd-6e5c-4fdc-9121-cc033f3d7e44
4ec0413a-631a-439c-9582-c80b7054880c
076b6c4b-404f-4acf-a109-0f0af9760ff8
\.


--
-- TOC entry 2898 (class 0 OID 17561)
-- Dependencies: 301
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, password, role, uid, worker, registrator) FROM stdin;
3	a.overko.olivija@gmail.com	QXM3MjE0NTY=	manager	80f48748-9e22-11e9-a2a3-2a2ae2dbcce4	2	1
4	katyaivanova10.04@gmail.com	OTQzODUy	analyser	6dda3c0e-1fc6-4998-81dc-f3ba5b41e5c1	3	1
2	stasvasilin@gmail.com	NTM3MjE0NTY=	admin	OlololoTrololo	1	1
\.


--
-- TOC entry 2913 (class 0 OID 18092)
-- Dependencies: 316
-- Data for Name: v1; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.v1 (last_value) FROM stdin;
4
\.


--
-- TOC entry 2902 (class 0 OID 17571)
-- Dependencies: 305
-- Data for Name: vro_crude; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vro_crude (id, humidity_after, humidity_before, huskiness, kernel_offset, pulp_humidity_1, soreness_after, soreness_before, "time", create_time, creator, turn, pulp_humidity_2) FROM stdin;
25	0.0900000036	0.119999997	0.0799999982	0.0799999982	0.0700000003	0.0900000036	0.109999999	2019-07-05 17:30:00	24	1	23	0.100000001
28	0.150000006	0.159999996	0.119999997	0.129999995	0.119999997	0.140000001	0.170000002	2019-07-05 14:30:00	27	1	23	0.150000006
\.


--
-- TOC entry 2904 (class 0 OID 17577)
-- Dependencies: 307
-- Data for Name: vro_dailys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vro_dailys (id, husk_humidity, husk_percent, husk_soreness, kernel_humidity, kernel_percent, create_time, creator, turn) FROM stdin;
\.


--
-- TOC entry 2906 (class 0 OID 17582)
-- Dependencies: 309
-- Data for Name: vro_oil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vro_oil (id, acid, color, peroxide, phosphorus, create_time, creator, turn) FROM stdin;
31	0.0599999987	7	0.0599999987	0.0500000007	30	1	23
\.


--
-- TOC entry 2909 (class 0 OID 17590)
-- Dependencies: 312
-- Data for Name: weights; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.weights (id, brutto, tara, uid, brutto_time, tara_time, correction) VALUES(
  65,	38.5,	16.2199993,	'a1c649a4-cd74-4294-aa0c-f230ff6bf79d',	63,	64,	0.635406494
);
INSERT INTO public.weights (id, brutto, tara, uid, brutto_time, tara_time, correction) VALUES(
  101,	40.5,	13.3000002,	'e736890b-a7c0-4695-a382-1666c85e95dc',	99,	100,	0
);
INSERT INTO public.weights (id, brutto, tara, uid, brutto_time, tara_time, correction) VALUES(
  143,	0,	0,	'98f552dd-6e5c-4fdc-9121-cc033f3d7e44',	NULL,	NULL,	0
);




--
-- TOC entry 2919 (class 0 OID 0)
-- Dependencies: 197
-- Name: act_numbers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.act_numbers_id_seq', 1, false);


--
-- TOC entry 2920 (class 0 OID 0)
-- Dependencies: 199
-- Name: action_time_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.action_time_id_seq', 1968, false);


--
-- TOC entry 2921 (class 0 OID 0)
-- Dependencies: 201
-- Name: administration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.administration_id_seq', 1, false);


--
-- TOC entry 2922 (class 0 OID 0)
-- Dependencies: 203
-- Name: analyses_cake_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.analyses_cake_id_seq', 1730, false);


--
-- TOC entry 2923 (class 0 OID 0)
-- Dependencies: 205
-- Name: analyses_oil_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.analyses_oil_id_seq', 1, false);


--
-- TOC entry 2924 (class 0 OID 0)
-- Dependencies: 207
-- Name: analyses_sun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.analyses_sun_id_seq', 1, false);


--
-- TOC entry 2925 (class 0 OID 0)
-- Dependencies: 212
-- Name: bot_user_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bot_user_settings_id_seq', 1, false);


--
-- TOC entry 2926 (class 0 OID 0)
-- Dependencies: 214
-- Name: change_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.change_log_id_seq', 1, false);


--
-- TOC entry 2927 (class 0 OID 0)
-- Dependencies: 216
-- Name: changes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.changes_id_seq', 1, false);


--
-- TOC entry 2928 (class 0 OID 0)
-- Dependencies: 218
-- Name: deals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.deals_id_seq', 1, false);


--
-- TOC entry 2929 (class 0 OID 0)
-- Dependencies: 221
-- Name: drivers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.drivers_id_seq', 1, false);


--
-- TOC entry 2930 (class 0 OID 0)
-- Dependencies: 223
-- Name: extraction_crude_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_crude_id_seq', 1, false);


--
-- TOC entry 2931 (class 0 OID 0)
-- Dependencies: 225
-- Name: extraction_oil_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_oil_id_seq', 1, false);


--
-- TOC entry 2932 (class 0 OID 0)
-- Dependencies: 227
-- Name: extraction_raw_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_raw_id_seq', 1, false);


--
-- TOC entry 2933 (class 0 OID 0)
-- Dependencies: 229
-- Name: extraction_storage_grease_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_storage_grease_id_seq', 1, false);


--
-- TOC entry 2934 (class 0 OID 0)
-- Dependencies: 231
-- Name: extraction_storage_protein_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_storage_protein_id_seq', 1, false);


--
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 233
-- Name: extraction_turn_grease_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_turn_grease_id_seq', 1, false);


--
-- TOC entry 2936 (class 0 OID 0)
-- Dependencies: 235
-- Name: extraction_turn_protein_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.extraction_turn_protein_id_seq', 1, false);


--
-- TOC entry 2937 (class 0 OID 0)
-- Dependencies: 238
-- Name: forpress_cake_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forpress_cake_id_seq', 1, false);


--
-- TOC entry 2938 (class 0 OID 0)
-- Dependencies: 239
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 177, true);


--
-- TOC entry 2939 (class 0 OID 0)
-- Dependencies: 241
-- Name: kpo_parts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.kpo_parts_id_seq', 1, false);


--
-- TOC entry 2940 (class 0 OID 0)
-- Dependencies: 243
-- Name: laboratory_turns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.laboratory_turns_id_seq', 1, false);


--
-- TOC entry 2941 (class 0 OID 0)
-- Dependencies: 245
-- Name: load_plans_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.load_plans_id_seq', 1, false);


--
-- TOC entry 2942 (class 0 OID 0)
-- Dependencies: 247
-- Name: oil_mass_fraction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.oil_mass_fraction_id_seq', 1, false);


--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 249
-- Name: oil_mass_fractions_dry_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.oil_mass_fractions_dry_id_seq', 1, false);


--
-- TOC entry 2944 (class 0 OID 0)
-- Dependencies: 251
-- Name: organisation_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.organisation_types_id_seq', 8, true);


--
-- TOC entry 2945 (class 0 OID 0)
-- Dependencies: 253
-- Name: organisations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.organisations_id_seq', 1, true);


--
-- TOC entry 2946 (class 0 OID 0)
-- Dependencies: 255
-- Name: persons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persons_id_seq', 7, true);


--
-- TOC entry 2947 (class 0 OID 0)
-- Dependencies: 257
-- Name: phones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.phones_id_seq', 1, false);


--
-- TOC entry 2948 (class 0 OID 0)
-- Dependencies: 259
-- Name: probe_oil_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.probe_oil_id_seq', 1, false);


--
-- TOC entry 2949 (class 0 OID 0)
-- Dependencies: 261
-- Name: probe_oilcake_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.probe_oilcake_id_seq', 1, false);


--
-- TOC entry 2950 (class 0 OID 0)
-- Dependencies: 263
-- Name: probe_sun_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.probe_sun_id_seq', 1, false);


--
-- TOC entry 2951 (class 0 OID 0)
-- Dependencies: 266
-- Name: product_properties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_properties_id_seq', 1, false);


--
-- TOC entry 2952 (class 0 OID 0)
-- Dependencies: 268
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 5, true);


--
-- TOC entry 2953 (class 0 OID 0)
-- Dependencies: 271
-- Name: seals_batch_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seals_batch_id_seq', 1, false);


--
-- TOC entry 2954 (class 0 OID 0)
-- Dependencies: 272
-- Name: seals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seals_id_seq', 1, false);


--
-- TOC entry 2955 (class 0 OID 0)
-- Dependencies: 274
-- Name: storage_analyses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storage_analyses_id_seq', 1, false);


--
-- TOC entry 2956 (class 0 OID 0)
-- Dependencies: 276
-- Name: storage_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storage_product_id_seq', 7, true);


--
-- TOC entry 2957 (class 0 OID 0)
-- Dependencies: 278
-- Name: storage_turns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storage_turns_id_seq', 1, false);


--
-- TOC entry 2958 (class 0 OID 0)
-- Dependencies: 280
-- Name: storages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storages_id_seq', 7, true);


--
-- TOC entry 2959 (class 0 OID 0)
-- Dependencies: 283
-- Name: trains_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trains_id_seq', 1, false);


--
-- TOC entry 2960 (class 0 OID 0)
-- Dependencies: 285
-- Name: transportation_notes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transportation_notes_id_seq', 1, false);


--
-- TOC entry 2961 (class 0 OID 0)
-- Dependencies: 287
-- Name: transportations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transportations_id_seq', 1, false);


--
-- TOC entry 2962 (class 0 OID 0)
-- Dependencies: 289
-- Name: truck_transportation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.truck_transportation_id_seq', 1, false);


--
-- TOC entry 2963 (class 0 OID 0)
-- Dependencies: 290
-- Name: truck_transportation_truck_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.truck_transportation_truck_seq', 1, false);


--
-- TOC entry 2964 (class 0 OID 0)
-- Dependencies: 292
-- Name: trucks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trucks_id_seq', 1, false);


--
-- TOC entry 2965 (class 0 OID 0)
-- Dependencies: 296
-- Name: turns_extraction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.turns_extraction_id_seq', 1, false);


--
-- TOC entry 2966 (class 0 OID 0)
-- Dependencies: 297
-- Name: turns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.turns_id_seq', 1, false);


--
-- TOC entry 2967 (class 0 OID 0)
-- Dependencies: 299
-- Name: turns_vro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.turns_vro_id_seq', 1, false);


--
-- TOC entry 2968 (class 0 OID 0)
-- Dependencies: 302
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 4, true);


--
-- TOC entry 2969 (class 0 OID 0)
-- Dependencies: 304
-- Name: vehicles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicles_id_seq', 1, false);


--
-- TOC entry 2970 (class 0 OID 0)
-- Dependencies: 306
-- Name: vro_crude_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vro_crude_id_seq', 1, false);


--
-- TOC entry 2971 (class 0 OID 0)
-- Dependencies: 308
-- Name: vro_dailys_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vro_dailys_id_seq', 1, false);


--
-- TOC entry 2972 (class 0 OID 0)
-- Dependencies: 310
-- Name: vro_oil_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vro_oil_id_seq', 1, false);


--
-- TOC entry 2973 (class 0 OID 0)
-- Dependencies: 313
-- Name: weights_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.weights_id_seq', 1, false);


--
-- TOC entry 2974 (class 0 OID 0)
-- Dependencies: 315
-- Name: workers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.workers_id_seq', 3, true);


-- Completed on 2019-07-26 09:29:48

--
-- PostgreSQL database dump complete
--

