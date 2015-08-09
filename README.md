# sport-cloudsql
Uitwerking java sport app met Cloud SQL en MemCache met App Engine
http://pdvsport.appspot.com/

project is opgezet met Eclipse App Engine plugin. Er is gebruik gemaakt van JavaBeans, EL en JSTL.

Servlet krijgt een instantie van Administratie. Deze maakt gebruik van MemCache en óf 
DatastoreIO óf MySqlIO.

Instructies om Cloud SQL op te zetten:
https://cloud.google.com/appengine/docs/java/cloud-sql/
