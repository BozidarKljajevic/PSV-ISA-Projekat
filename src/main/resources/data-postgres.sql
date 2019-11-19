INSERT INTO public.pacijent(
	id, adresa, aktivan, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('pacijent_id_seq'), 'Danila Kisa 5', true, '+381/65-5504205', 'Srbija', 'Novi Sad', 'Damjan', 'panticdamjan@fmail.com', 'Pantic', '1234');

INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, grad, ime, lozinka, mail, prezime)
	VALUES (nextval('admin_klinike_id_seq'),'Janka Veselinovica 17', '0691411997', 'Srbija', 'Novi Sad', 'Damjan', 'damjanbanjac', 'damjanbanjac14@gmail.com', 'Banjac');