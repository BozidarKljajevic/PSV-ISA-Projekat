INSERT INTO public.pacijent(
	id, adresa, aktivan, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('pacijent_id_seq'), 'Danila Kisa 5', true, '+381/65-5504205', 'Srbija', 'Novi Sad', 'Damjan', 'panticdamjan@fmail.com', 'Pantic', '1234');
	
INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Jovana Obrenovica 83A', '+381/61-5534209', 'Crna Gora', 'Pljevlja', 'Sveti vid', 3.5, 'Vrlo lepo');


INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, grad, ime, lozinka, mail, prezime,klinika_id)
	VALUES (nextval('admin_klinike_id_seq'),'Janka Veselinovica 17', '+381/64-7554608', 'Srbija', 'Novi Sad', 'Damjan', 'damjanbanjac', 'damjanbanjac14@gmail.com', 'Banjac',1);

INSERT INTO public.admin_centra(
	mail, lozinka)
	VALUES ('admin', 'admin');
	
INSERT INTO public.medicinsko_osoblje(
	id, adresa, broj_telefona, drzava, grad, ime, lekar, lozinka, mail, prezime, radno_do, radno_od, klinika_id)
	VALUES (nextval('medicinsko_osoblje_id_seq'), 'Koste Racina 15', '+381/64-7554699', 'Srbija', 'Beograd', 'John', true, 'johnisner', 'damjanbanjac@gmail.com', 'Isner', '20:00', '10:00', 1);