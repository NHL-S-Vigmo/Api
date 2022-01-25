Als ik een nieuwe media slide toevoeg moet ik een keer 'Audio enabled' hebben aangeklikt anders krijg ik een foutmelding.

In de toekomst zou ik eens gaan kijken naar Redux voor het gebruik van een datastore in de frontend. Hoef je ook minder vaak data op te halen!

De reflectie voor de ID's vond ik wel heel gek. Waarom zou je daar geen interface gebruiken?

@Generated boven iets neerzetten wat je zelf hebt geschreven is niet de bedoeling.

Nu ik de tijd heb om de code goed te lezen, denk ik ook wel dat alle services testen overbodig was, behalve de extra methodes natuurlijk. Controllers moeten wel allemaal, want dan zit je met dingen zoals validatie en security etc.

Maar verder, ontzettend fraai project! Jullie hebben een simpele CRUD applicatie erg netjes neergezet. Houdt er wel rekening mee dat het complexer kan worden zodra je meer repo's nodig hebt in services, bijvoorbeeld wanneer de relaties tussen entiteiten complexer worden. Bij het testen zijn jullie zelfs wat te ver gegaan. Heel goed bezig, maar door het (onnodige) mocken worden de tests wel lastiger leesbaar.
