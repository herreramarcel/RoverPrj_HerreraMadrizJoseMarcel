import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
public class Rover{
    
private String nombre;
private double potenciaInicial;
private double potenciaActual;
private int recargasDisponibles;
private int posicionActualX;
private int posicionActualY;
private int posicionInicialX;
private int posicionInicialY;
private int cantidadDetecciones;
private double costoMovimiento;
private String codigoRover;
private List<List<String>> mandatosExitosos;
private List<List<String>> mandatosRechazados;
private double costoDeteccion;
 
public Rover(String nombre) {
 this(nombre, 100.0);
 }
 
 public Rover(String nombre, double potencia) {
   this.nombre = nombre;
   potenciaInicial = potencia;
   potenciaActual = potencia;
   posicionInicialX = 0;
   posicionInicialY = 0;
   posicionActualX = posicionInicialX;
   posicionActualY = posicionInicialY;
   cantidadDetecciones = 0;
   mandatosExitosos = new ArrayList<>();
   mandatosRechazados = new ArrayList<>();
   costoMovimiento = 0.50;
   costoDeteccion = 0.25;
   recargasDisponibles = 5;
   codigoRover = "RVR-" + System.currentTimeMillis() % 100000;
 }
 
 public void desplazarseIzquierda() {
     if (hayPotenciaSuficiente()){
         if(!detectarFuga()){
             posicionActualX -=1;
             potenciaActual -= costoMovimiento;
             registrarMandato("Desplazamiento Izquierda", "Posible");
            }
            else{
                registrarMandato("Desplazamiento Izquierda", "Rechazado: fuga detectada");
            }
            }
            else{
             registrarMandato("Desplazamiento Izquierda", "No posible: potencia insuficiente");   
            }
            }

 public void desplazarseDerecha() {
     if (hayPotenciaSuficiente()){
         if(!detectarFuga()){
             posicionActualX +=1;
             potenciaActual -= costoMovimiento;
             registrarMandato("Desplazamiento Derecha", "Posible");
            }
            else{
                registrarMandato("Desplazamiento Derecha", "Rechazado: fuga detectada");
            }
            }
            else{
             registrarMandato("Desplazamiento Derecha", "No posible: potencia insuficiente");   
            }
            }
            
 public void desplazarseArriba() {
     if (hayPotenciaSuficiente()){
         if(!detectarFuga()){
             posicionActualY +=1;
             potenciaActual -= costoMovimiento;
             registrarMandato("Desplazamiento Arriba", "Posible");
            }
            else{
                registrarMandato("Desplazamiento Arriba", "Rechazado: fuga detectada");
            }
            }
            else{
             registrarMandato("Desplazamiento Arriba", "No posible: potencia insuficiente");   
            }
            }
            
 public void desplazarseAbajo() {
     if (hayPotenciaSuficiente()){
         if(!detectarFuga()){
             posicionActualY -=1;
             potenciaActual -= costoMovimiento;
             registrarMandato("Desplazamiento Abajo", "Posible");
            }
            else{
                registrarMandato("Desplazamiento Abajo", "Rechazado: fuga detectada");
            }
            }
            else{
             registrarMandato("Desplazamiento Abajo", "No posible: potencia insuficiente");   
            }
            }
            
private boolean detectarFuga() {
        potenciaActual -= costoDeteccion;
        cantidadDetecciones +=1;
        Random random = new Random();
        return random.nextDouble() >= 0.5;
}

public String conocerPosicionActual() {
    return "posición actual: "+posicionActualX+","+posicionActualY;
}

public String conocerPotenciaActual() {
    return "Potencia actual es de: "+potenciaActual;
}

public void recargarPotencia(double potencia) {
    if (esRecargaPosible()) {
        potenciaActual +=potencia;
        recargasDisponibles -= 1;
        registrarMandato("Recarga (" + potencia + ")", "Posible");
    } else {
        registrarMandato("Recarga (" + potencia + ")", "No posible: recargas agotadas");
    }
}

private boolean esRecargaPosible() {
    if (recargasDisponibles > 0){
        return true;
    } else {
        return false;
    }
}

private boolean hayPotenciaSuficiente(){
    if (potenciaActual >= costoMovimiento + costoDeteccion){
        return true;
    } else{
        return false;
    }
}

private String determinarFechaHoraActual() {
    Date fecha = new Date(System.currentTimeMillis());
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    return formatoFecha.format(fecha);
}

private void registrarMandato(String tipoMandato, String estatusMandato) {
    ArrayList<String> mandato = new ArrayList<>();
    mandato.add(tipoMandato);
    mandato.add(estatusMandato);
    mandato.add(determinarFechaHoraActual());
    if ("Exitoso".compareTo(estatusMandato) == 0) {
     mandatosExitosos.add(mandato);
    } else {
       mandatosRechazados.add(mandato);
 }
 }
 
public String toString() {
    String msg = "";

    msg += "========== Ficha del Rover ==========\n";
    msg += "Código: " + codigoRover + "\n";
    msg += "Nombre: " + nombre + "\n";
    msg += "Potencia (inicial/disponible): " + String.format("%.2f / %.2f", potenciaInicial, potenciaActual) + "\n";
    msg += "Posición (inicial → actual): (" + posicionInicialX + "," + posicionInicialY + ") → (" + posicionActualX + "," + posicionActualY + ")\n";
    msg += "Costos (mov/detección): " + String.format("%.2f / %.2f", costoMovimiento, costoDeteccion) + "\n";
    msg += "Recargas (realizadas/máximas): " + (5 - recargasDisponibles) + "/5\n";
    msg += "Detecciones de fuga realizadas: " + cantidadDetecciones + "\n";
    msg += "=====================================\n\n";
    msg += "---- Registro de Mandatos EXITOSOS ----\n";
    msg += String.format(" %-4s %-17s %-30s %-20s%n", "N°", "Fecha", "Mandato", "Estado");
    for (int i = 0; i < mandatosExitosos.size(); i++) {
      List<String> m = mandatosExitosos.get(i);
      String tipo = (m.size() > 0) ? m.get(0) : "";
      String estado = (m.size() > 1) ? m.get(1) : "";
      String fecha = (m.size() > 2) ? m.get(2) : "";
      msg += String.format(" %-4d %-17s %-30s %-20s%n", (i + 1), fecha, tipo, estado);
    }
    if (mandatosExitosos.isEmpty()) {
      msg += " (sin registros)\n";
    }
    msg += "\n";
    msg += "---- Registro de Mandatos FALLIDOS ----\n";
    msg += String.format(" %-4s %-17s %-30s %-20s%n", "N°", "Fecha", "Mandato", "Estado");
    for (int i = 0; i < mandatosRechazados.size(); i++) {
      List<String> m = mandatosRechazados.get(i);
      String tipo = (m.size() > 0) ? m.get(0) : "";
      String estado = (m.size() > 1) ? m.get(1) : "";
      String fecha = (m.size() > 2) ? m.get(2) : "";
      msg += String.format(" %-4d %-17s %-30s %-20s%n", (i + 1), fecha, tipo, estado);
    }
    if (mandatosRechazados.isEmpty()) {
      msg += " (sin registros)\n";
    }
    return msg;
    }

}
   
