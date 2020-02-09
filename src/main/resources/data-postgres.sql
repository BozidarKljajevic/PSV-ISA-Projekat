-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123

-- Data for Name: admin_centra; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.admin_centra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'admin@gmail.com', 'Pantic', true, '$2a$10$mzFkdgfA8T.UiOJLXLIe7.tcEgaoUyXDpDuBlsAZQrqQoDRyMw6Ae');
INSERT INTO public.admin_centra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Popa Steve 2', '+381/65-68955', 'Srbija', true, 'Beograd', 'Admin1', 'admin1@gmail.com', 'Admin1', false, '$2a$10$uhUImbNJFEPAhY2VLBm.zOfcfuKAHNldnAqEioe7jB2zZ1pjT1WJC');
INSERT INTO public.admin_centra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Prote Mateje 15', '+381/65-788655', 'Crna Gora', true, 'Pljevlja', 'Admin2', 'admin2@gmail.com', 'Admin2', false, '$2a$10$gebWSceCxnGSzK4LahjqYeyQqn9HC9zRgv7UdygAl/8eRXPIPnoAW');


--
-- Data for Name: klinika; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.klinika (id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis) VALUES (nextval('klinika_id_seq'), 'Janka Veselinovica 17', '+381/62-5475122', 'Srbija', 'Novi Sad', 'Klinika1', 4, 'Klinika1');
INSERT INTO public.klinika (id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis) VALUES (nextval('klinika_id_seq'), 'Knez Mihajlova 6', '+381/65-687895', 'Srbija', 'Beograd', 'Klinika2', 0, 'Klinika2');
INSERT INTO public.klinika (id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis) VALUES (nextval('klinika_id_seq'), 'Josipa Broza Tita 7', '+381/65-866955', 'Crna Gora', 'Podgorica', 'Klinika3', 0, 'Klinika3');


--
-- Data for Name: admin_klinike; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.admin_klinike (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, klinika_id) VALUES (nextval('users_id_seq'), 'Joke Provkica 15', '+381/65-0584555', 'Srbija', true, 'Beograd', 'AdminKlinike1', 'adminklinike1@gmail.com', 'AdminKlinike1', true, '$2a$10$ocmV7LyxzplhdO598ZaHwerhyKLAlWH1q14u/4SyVGXc4POvoWInG', 1);
INSERT INTO public.admin_klinike (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, klinika_id) VALUES (nextval('users_id_seq'), 'Janka Cmelnika 16', '+381/65-67856', 'Srbija', true, 'Novi Sad', 'AdminKlinike2', 'adminklinike2@gmail.com', 'AdminKlinike2', true, '$2a$10$6slNAaaIYviluC2TaQ.18OLgsJsSASouqyfwtrgT4M74tm27nIjme', 2);
INSERT INTO public.admin_klinike (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, klinika_id) VALUES (nextval('users_id_seq'), 'Ive Love Ribara', '+381/65-689986', 'Crna Gora', true, 'Budva', 'AdminKlinike3', 'adminklinike3@gmail.com', 'AdminKlinike3', true, '$2a$10$Y/QbCA6NgBoGOFs.7YnrDuUXBtsu3Rd2TXInIWDctiPfOmuCo4Oda', 3);
INSERT INTO public.admin_klinike (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, klinika_id) VALUES (nextval('users_id_seq'), 'Cara Dusana 66', '+381/63-558955', 'Srbija', true, 'Smederevo', 'AdminKlinike11', 'adminklinike11@gmail.com', 'AdminKlinike11', false, '$2a$10$SFkX52PiMds7RrsB279Oue003bgk7jE3lSz3g4uplTXyWO2Ov2Twm', 1);

--
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.authority (id, name) VALUES (1, 'PACIJENT');
INSERT INTO public.authority (id, name) VALUES (2, 'LEKAR');
INSERT INTO public.authority (id, name) VALUES (3, 'ADMIN');
INSERT INTO public.authority (id, name) VALUES (4, 'ADMINCENTRA');
INSERT INTO public.authority (id, name) VALUES (5, 'MEDICINSKASESTRA');


--
-- Data for Name: bolesti; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bolesti (sifra, naziv) VALUES (1, 'Bolest1');
INSERT INTO public.bolesti (sifra, naziv) VALUES (2, 'Bolest2');
INSERT INTO public.bolesti (sifra, naziv) VALUES (3, 'Bolest3');


--
-- Data for Name: tip_pregleda; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '2000', 'Tip1', '1', 1);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '6000', 'Tip2', '2', 1);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '1000', 'Tip3', '3', 1);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '6000', 'Tip1', '1', 2);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '1500', 'Tip3', '3', 2);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '1500', 'Tip2', '2', 3);
INSERT INTO public.tip_pregleda (id, cena, naziv, oznaka, klinika_id) VALUES (nextval('tippregleda_id_seq'), '500', 'Tip4', '4', 3);


--
-- Data for Name: lekar; Type: TABLE DATA; Schema: public; Owner: postgres
--

--
-- Data for Name: medicinska_sestra; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Sofe Svemirca 56', '+381/65-68965', 'Srbija', true, 'Prijepolje', 'Lekar11', 'lekar11@gmail.com', 'Lekar11', true, '$2a$10$zmvzBBKZNoiFft4Z4cVd0un.opsxQBpSD7j8vUPxspO5dg6jcQRAu', '5.0', '20:00', '14:00', 1, 1);
INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Ilije Bircanina 45', '+381/65-689875', 'Srbija', true, 'Jagodina', 'Lekar12', 'lekar12@gmail.com', 'Lekar12', false, '$2a$10$94X/FuWbctSrOVPzUbwSOupXgWsf/w1FKBgl62eyiNqMBaJjtJnay', '0', '12:00', '09:00', 1, 1);
INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Jove Jovica 45', '+381/65-68955', 'Srbija', true, 'Novi Sad', 'Lekar13', 'lekar13@gmail.com', 'Lekar13', false, '$2a$10$dAip7FGul9R1YVmNP/76jucP60GrjdRSRg4SY.gp/bGw2nCvLF.6G', '0', '15:00', '11:00', 1, 2);
INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Svete Sofrica 59', '+381/65-665455', 'Crna Gora', true, 'Podgorica', 'Lekar14', 'lekar14@gmail.com', 'Lekar14', false, '$2a$10$3OF5y/RiU07tld9f9DJ9vO0.Ei6AYyvqH06PrTRjcvYF.3zi7/iH.', '0', '20:00', '17:00', 1, 2);

INSERT INTO public.medicinska_sestra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, radno_do, radno_od, klinika_id) VALUES (nextval('users_id_seq'), 'Ive Lole Ribara 8', '+381/65-686985', 'Srbija', true, 'Novi Sad', 'Sestra11', 'sestra11@gmail.com', 'Sestra11', false, '$2a$10$KgPoRnqGeXU4wrmb9JQs0ewsqPhE4YVBgfpvDgEpq5vVveFDjRtJC', '20:00', '12:00', 1);
INSERT INTO public.medicinska_sestra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, radno_do, radno_od, klinika_id) VALUES (nextval('users_id_seq'), 'Jove Jove 69', '+381/65-66585', 'Srbija', true, 'Beograd', 'Sestra12', 'sestra12@gmail.com', 'Sestra12', false, '$2a$10$Gq2GMkgPzxVxOrpxPXdlBejF04L9Dx02lVXs/iXckj4R/v1C9NvTe', '17:00', '09:00', 1);

INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Jovana Jole 65', '+381/65-68955', 'Srbija', true, 'Zrenjanin', 'Lekar21', 'lekar21@gmail.com', 'Lekar21', true, '$2a$10$eCcqKNjr0BWiFzsptz0KdeVKJDy8Fk5KVfKQkcNC/SP/NKMaJASAS', '0', '20:00', '15:00', 2, 4);
INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Ilije Bricanina 55', '+381/65-69862', 'Srbija', true, 'Kragujevac', 'Lekar22', 'lekar22@gmail.com', 'Lekar22', false, '$2a$10$JBL.rUyTCF7zbqMXYC2h1OJIMruen.zDsMuyzehvl0Ky0kzmBtvUu', '0', '15:00', '12:00', 2, 4);
INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Pope Joce 7', '+381/65-686255', 'Crna Gora', true, 'Pljevlja', 'Lekar23', 'lekar23@gmail.com', 'Lekar23', false, '$2a$10$ZcoYt2ZKQ3vrEwzvMtWuceZOpcSVBmR7gnWHONgtMl.5EZdbR.12u', '0', '13:00', '08:00', 2, 5);

INSERT INTO public.medicinska_sestra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, radno_do, radno_od, klinika_id) VALUES (nextval('users_id_seq'), 'Sestre Jovke 78', '+381/69-66955', 'Srbija', true, 'Negotin', 'Sestra21', 'sestra21@gmail.com', 'Sestra21', false, '$2a$10$mlzUoYTWR.6W5P/W23ZXmOmI31sy/9//DOSeUTXL6SzvMsKZqju6.', '20:00', '14:00', 2);

INSERT INTO public.lekar (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id) VALUES (nextval('users_id_seq'), 'Ilije Bircanina', '+381/65-68955', 'Srbija', true, 'Subotica', 'Lekar31', 'lekar31@gmail.com', 'Lekar31', false, '$2a$10$CxE9fzbamWT.BO1lbmnpheSQqyLLQT4vCW3AQuTkxUEInEiLUVTfi', '0', '20:00', '17:00', 3, 6);

INSERT INTO public.medicinska_sestra (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, radno_do, radno_od, klinika_id) VALUES (nextval('users_id_seq'), 'Joce Ducica 6', '+381/63-689500', 'Srbija', true, 'Novi Sad', 'Sestra31', 'sestra31@gmail.com', 'Sestra31', false, '$2a$10$KjXFAZtItFiHNE6cy0.OXuRIczbTmbA1FdGB6Tc7Bg5ADrZs5EDDq', '19:00', '12:00', 3);


--
-- Data for Name: godisnji; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.godisnji (id, datum_do, datum_od, odobren, lekar_id, sestra_id) VALUES (nextval('godisnji_id_seq'), '2020-05-06', '2020-04-15', true, 8, NULL);
INSERT INTO public.godisnji (id, datum_do, datum_od, odobren, lekar_id, sestra_id) VALUES (nextval('godisnji_id_seq'), '2020-04-07', '2020-03-27', false, 14, NULL);


--
-- Data for Name: pacijent; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pacijent (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-989632', 'Srbija', true, 'Novi Sad', 'Dado', 'panticdamjan@gmail.com', 'Pantic', true, '$2a$10$R7yU5Mh9MBHogZV9cQRT2Ob2C3F8vrwEy9CbtijzkMrw4Dan.J/vS');
INSERT INTO public.pacijent (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Jove Jocica 15', '+381/62-675123', 'Srbija', false, 'Smederevo', 'Pacijent1', 'leagueofpro75@gmail.com', 'Pacijent1', true, '$2a$10$35MtzKyptsMiHbjFs82Y7.OA70n2SgHVINf2yqa6BFTrsVMvRn4.m');
INSERT INTO public.pacijent (id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra) VALUES (nextval('users_id_seq'), 'Ive Lole Ribara', '+381/66-687542', 'Srbija', true, 'Novi Sad', 'Pacijent2', 'pacijent2@gmail.com', 'Pacijent2', true, '$2a$10$yFZoX3xvUt4onMZTYEEzeu5s4fqxP44.NwsApj1ejabKVGLpGnfkm');


--
-- Data for Name: karton; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.karton (id, datum_rodjenja, dioptrija, krvna_grupa, pol, tezina, visina, pacijent_id) VALUES (nextval('karton_id_seq'), '22/08/1997', -0.5, 'A+', 'Muski', 82, 187, 20);
INSERT INTO public.karton (id, datum_rodjenja, dioptrija, krvna_grupa, pol, tezina, visina, pacijent_id) VALUES (nextval('karton_id_seq'), 'Potrebno uneti', 0, 'Potrebno uneti', 'Potrebno uneti', 0, 0, 21);
INSERT INTO public.karton (id, datum_rodjenja, dioptrija, krvna_grupa, pol, tezina, visina, pacijent_id) VALUES (nextval('karton_id_seq'), '09/01/1994', 1, 'AB+', 'Zenski', 62, 163, 22);


--
-- Data for Name: recept; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.recept (id, overen, sestra_id) VALUES (nextval('recept_id_seq'), false, NULL);


--
-- Data for Name: izvestajopregledu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.izvestajopregledu (id, bolest_id, karton_id, lekar_id, pacijent_id, recept_id) VALUES (nextval('izvestaj_id_seq'), 1, 1, 8, 20, 1);


--
-- Data for Name: lek; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lek (sifra, naziv) VALUES (1, 'Lek1');
INSERT INTO public.lek (sifra, naziv) VALUES (2, 'Lek2');
INSERT INTO public.lek (sifra, naziv) VALUES (3, 'Lek3');


--
-- Data for Name: sala_klinike; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '1', 'Sala1', 1);
INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '2', 'Sala2', 1);
INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '3', 'Sala3', 1);
INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '1', 'Sala1', 2);
INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '2', 'Sala2', 2);
INSERT INTO public.sala_klinike (id, broj, naziv, klinika_id) VALUES (nextval('salaklinike_id_seq'), '1', 'Sala1', 3);


--
-- Data for Name: operacija; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.operacija (id, cena, datum, id_pacijenta, trajanje_operacije, vreme, zavrsen, sala_klinike_id, tip_operacije_id) VALUES (nextval('operacija_id_seq'), 2000, '27/01/2020', 20, 1, '15:00', false, 1, 1);


--
-- Data for Name: lekari_operacije; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lekari_operacije (operacija_id, lekar_id) VALUES (1, 8);


--
-- Data for Name: lekovi_izvestaji; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lekovi_izvestaji (izvestaj_id, lek_id) VALUES (1, 1);
INSERT INTO public.lekovi_izvestaji (izvestaj_id, lek_id) VALUES (1, 2);


--
-- Data for Name: lekovi_recept; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lekovi_recept (recept_id, lek_id) VALUES (1, 1);
INSERT INTO public.lekovi_recept (recept_id, lek_id) VALUES (1, 2);


--
-- Data for Name: ocena_klinika; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ocena_klinika (id, ocena, klinika_id, pacijent_id) VALUES (nextval('ocenaklinika_id_seq'), 4, 1, 20);


--
-- Data for Name: ocena_lekar; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ocena_lekar (id, ocena, lekar_id, pacijent_id) VALUES (nextval('ocenalekar_id_seq'), 5, 8, 20);


--
-- Data for Name: pregledi; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 2000, '04/03/2020', 20, 1, '15:00', true, 8, 1, 1);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 2000, '01/04/2020', NULL, 1.5, '18:00', false, 8, 2, 1);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 6000, '09/03/2020', NULL, 0.5, '12:00', false, 10, 2, 2);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 6000, '11/03/2020', NULL, 1.5, '16:00', false, 14, 4, 4);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 1500, '05/04/2020', 20, 1, '10:00', false, 16, 4, 5);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 1500, '03/03/2020', NULL, 2, '17:00', false, 18, 6, 6);
INSERT INTO public.pregledi (id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('pregled_id_seq'), 2000, '06/03/2020', 20, 0.5, '15:00', false, 8, 1, 1);


--
-- Data for Name: user_authority; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_authority (user_id, authority_id) VALUES (1, 4);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (2, 4);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (3, 4);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (4, 3);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (5, 3);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (6, 3);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (7, 3);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (8, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (9, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (10, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (11, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (12, 5);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (13, 5);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (14, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (15, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (16, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (17, 5);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (18, 2);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (19, 5);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (20, 1);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (21, 1);
INSERT INTO public.user_authority (user_id, authority_id) VALUES (22, 1);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: zahtev; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 2000, '20/03/2020', 20, true, 0.5, '10:00', 9, NULL, NULL, NULL, 1);
INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 1500, '13/02/2020', 20, true, 0.5, '9:00', 16, NULL, NULL, NULL, 5);
INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 2000, '10/03/2020', 20, false, 1, '15:00', 8, NULL, NULL, NULL, 1);
INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 2000, '11/03/2020', 20, true, 1.5, '15:30', 8, NULL, NULL, NULL, 1);
INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 2000, '02/03/2020', 20, false, 1.5, '15:30', 8, NULL, NULL, NULL, 1);
INSERT INTO public.zahtev (id, cena, datum, id_pacijenta, izbor, trajanje_pregleda, vreme, lekar_id, lekar1_id, lekar2_id, sala_klinike_id, tip_pregleda_id) VALUES (nextval('zahtev_id_seq'), 2000, '28/01/2020', 20, true, 0.5, '15:30', 8, NULL, NULL, 2, 1);

