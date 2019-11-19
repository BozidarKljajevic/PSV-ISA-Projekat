INSERT INTO public.pacijent(
	id, adresa, aktivan, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('pacijent_id_seq'), 'Danila Kisa 5', true, '+381/65-5504205', 'Srbija', 'Novi Sad', 'Damjan', 'panticdamjan@fmail.com', 'Pantic', '1234');
	
INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Jovana Obrenovica 83A', '+382/68 899675', 'Crna Gora', 'Pljevlja', 'Sveti vid', 3.5, 'Vrlo lepo');


INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, grad, ime, lozinka, mail, prezime,klinika_id)
	VALUES (nextval('admin_klinike_id_seq'),'Janka Veselinovica 17', '0691411997', 'Srbija', 'Novi Sad', 'Damjan', 'damjanbanjac', 'damjanbanjac14@gmail.com', 'Banjac',1);

