package webservicerota

class Malha {
    
    String origem;
    String destino;
    Integer distancia;

    static belongsTo = [mapa: Mapa]

 }