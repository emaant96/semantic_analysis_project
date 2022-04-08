package esa;

import java.io.IOException;

public class MainEsa {

	public static void main(String[] args) throws IOException {

			String[] concetti= {"Religione","Biologia","Natura","Scienza","Storia","Architettura"};
			Interpreter ip=new Interpreter(concetti);
			/*String[] titoli= {"Arco di Tito",  
					"Catacombe di San Callisto" ,
					"Circo Massimo" ,
					"Palatino" ,
					"Colonna di Marco Aurelio" ,
					"Colonna Traiana" ,
					"Colosseo"  ,
					"Domus Aurea", 
					"Foro Boario" ,
					"Foro di Augusto", 
					"Foro di Nerva"  ,
					"Foro Romano" ,
					"Isola Tiberina",  
					"Largo di Torre Argentina", 
					"Statua equestre di Marco Aurelio", 
					"Monte Testaccio" ,
					"Mura serviane" ,
					"Necropoli vaticana", 
					"Obelischi di Roma", 
					"Piazza San Pietro",  
					"Portico di Ottavia" ,
					"Sepolcro degli Scipioni",
					"Tempio della Pace",
					"Area di Sant'Omobono",
					"Terme di Caracalla"};*/
			
			/*String[] titoli= {"Basilica di San Giovanni in Laterano",
					"Basilica di San Pietro in Vaticano",
					"Basilica di Santa Maria degli Angeli e dei Martiri",
					"Cappella Sistina", 
					"Casino Nobile", 
					"Chiesa del Gesù", 
					"Chiesa di San Pietro in Montorio",
					"Chiesa di Sant'Agnese in Agone",
					"Fontana del Tritone",
					"Fontana della Barcaccia",
					"Galleria Borghese",
					"Galleria nazionale d'arte moderna e contemporanea",
					"Statua equestre di Marco Aurelio",
					"MAXXI - Museo nazionale delle arti del XXI secolo",
					"Mosè (Michelangelo)", 
					"Musei Vaticani", 
					"Palazzo Barberini", 
					"Palazzo delle Esposizioni",
					"Piazza Navona",
					"Piazza San Pietro", 
					"Chiesa di San Carlo alle Quattro Fontane", 
					"Chiesa di Sant'Ivo alla Sapienza", 
					"Stadio di Domiziano",
					"Chiesa di Sant'Andrea al Quirinale"};*/
			String[] titoli= {/*
					"Basilica di San Giovanni in Laterano",
					"Basilica di San Pietro in Vaticano",
					"Basilica di San Paolo fuori le mura",
					"Basilica di Santa Maria Maggiore",
					"Cappella Sistina",
					"Piazza San Pietro", 
					"Scala Santa",
					"Basilica di San Lorenzo fuori le Mura",
					"Basilica di Santa Croce in Gerusalemme",
					"Chiesa del Gesù",
					*/"Pantheon (Roma)"/*,
					"Basilica di Santa Maria in Aracoeli",
					"Basilica di San Pietro in Vincoli",
					"Basilica di Santa Maria del Popolo",
					"Basilica di Santa Maria sopra Minerva",
					"Basilica di San Clemente al Laterano",
					"Basilica di Santa Maria in Trastevere",
					"Chiesa di San Luigi dei Francesi",
					"Chiesa di San Carlo alle Quattro Fontane",
					"Basilica di Santa Sabina",
					"Chiesa di Santa Maria della Vittoria (Roma)",
					"Basilica di Santa Maria in Cosmedin",
					"Mausoleo di Santa Costanza",
					"Monumento a Giordano Bruno",
					"Tempio di Antonino e Faustina",
					"Tempio di Portuno",*/
			};
			
			/*String[] titoli= {"Largo di Torre Argentina","Palazzo delle Esposizioni","Basilica di San Pietro in Vaticano""Basilica di San Giovanni in Laterano"};*/
			
			/*String[] titoli= {"Cattedrale di San Lorenzo (Perugia)",
					"Basilica di San Pietro (Perugia)",
					"Basilica di San Domenico (Perugia)",
					"Chiesa di San Michele Arcangelo (Perugia)",
					"Chiesa di San Costanzo (Perugia)",
					"Oratorio di San Bernardino (Perugia)",
					"Chiesa di San Matteo degli Armeni",
					"Chiesa di Santa Maria di Monteluce",
					"Chiesa di San Severo (Perugia)",
			};*/
			for(int i=0;i<titoli.length;i++) {
				ip.PoiInterpreter(titoli[i],concetti);
			}	
	}
}
