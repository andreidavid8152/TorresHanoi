import java.util.Stack;

public class TowerHanoi {
    private Stack<String> torreI;
    private Stack<String> torreP;
    private Stack<String> torreD;
    private int nDiscos;
    private int minIntentos;
    private int intentos;
    private Stack<String> movimientos;

    public TowerHanoi(){
        intentos = 0;
        nDiscos = 0;
        torreI = new Stack<>();
        torreP = new Stack<>();
        torreD = new Stack<>();
        movimientos = new Stack<>();
    }
    public Stack<String> getMovimientos() {
        return movimientos;
    }

    public int getMinIntentos() {
        return minIntentos;
    }

    public void setMinIntentos() {
        this.minIntentos = ((int)Math.pow(2, nDiscos))-1;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public Stack<String> getTorreI() {
        return torreI;
    }

    public Stack<String> getTorreP() {
        return torreP;
    }

    public Stack<String> getTorreD() {
        return torreD;
    }

    public void setnDiscos(int nDiscos) {
        this.nDiscos = nDiscos;
    }

    public int getnDiscos() {
        return nDiscos;
    }
    public void inicialApivote(){
        torreP.add(torreI.pop());
        intentos += 1;
    }
    public void inicialAdestino(){
        torreD.add(torreI.pop());
        intentos += 1;
    }
    public void pivoteAinicial(){
        torreI.add(torreP.pop());
        intentos += 1;
    }
    public void pivoteAdestino(){
        torreD.add(torreP.pop());
        intentos += 1;
    }
    public void destinoAinicial(){
        torreI.add(torreD.pop());
        intentos += 1;
    }
    public void destinoApivote(){
        torreP.add(torreD.pop());
        intentos += 1;
    }
    public boolean juegoCompletado(){
        return torreD.size() == nDiscos && torreI.isEmpty() && torreP.isEmpty();
    }

}
