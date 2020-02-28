public class RotationMethod {

    private double matrA[][];
    private double matrT[][];
    private double vectorB[];
    private double vectorX[];
    private double discrepancy[];
    private double sinFi;
    private double cosFi;
    private int n;

    RotationMethod(double matrA[][], double vectorB[]){
        n = matrA.length;
        this.matrT = new double[n][n];
        this.matrA = new double[n][n];
        this.vectorB = new double[n];
        for(int i = 0; i < n; i++){
            this.vectorB[i] = vectorB[i];
            for(int j = 0; j < n; j++){
                this.matrA[i][j] = matrA[i][j];
            }
        }
    }

    private void createMatrT(int firstIndex, int secondIndex){
        cosFi = matrA[firstIndex][firstIndex] / Math.sqrt(Math.pow(matrA[firstIndex][firstIndex], 2) + Math.pow(matrA[secondIndex][firstIndex], 2));
        sinFi = -matrA[secondIndex][firstIndex] / Math.sqrt(Math.pow(matrA[firstIndex][firstIndex], 2) + Math.pow(matrA[secondIndex][firstIndex], 2));
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrT[i][i] = 1;
                } else {
                    matrT[i][j] = 0;
                }
            }
        }
        if(matrA[secondIndex][firstIndex] != 0) {
            matrT[firstIndex][firstIndex] = cosFi;
            matrT[firstIndex][secondIndex] = -sinFi;
            matrT[secondIndex][secondIndex] = cosFi;
            matrT[secondIndex][firstIndex] = sinFi;
        }
    }


    private void multiplyMatrTWithMatrA(){
        double newMatr[][] = new double[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatr[i][j] = 0;
                for (int k = 0; k < n; k++)
                    newMatr[i][j] += matrT[i][k] * matrA[k][j];
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrA[i][j] = newMatr[i][j];
            }
        }
    }

    private void multiplyMatrTWithVectorB(){
        double newVector[] = new double[n];
        for(int i=0; i<n; i++)
        {
            newVector[i]=0;
            for(int j=0; j<n; j++)
            {
                newVector[i] += matrT[i][j]*vectorB[j];
            }
        }
        for(int i = 0; i < n; i++){
            vectorB[i] = newVector[i];
        }
    }

    public void rotationMethod(){
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                createMatrT(i, j);
                multiplyMatrTWithMatrA();
                multiplyMatrTWithVectorB();
            }
        }
    }

    public void createVectorX(){
        vectorX = new double[n];
        double sum;
        vectorX[n-1] = vectorB[n-1]/matrA[n-1][n-1];
        for(int i = n - 2; i >= 0; i--){
            sum = 0;
            for(int j = n-1; j > i; j--){
                sum += matrA[i][j] * vectorX[j];
            }
            vectorX[i] = (vectorB[i] - sum) / matrA[i][i];
        }
    }

    public void createDiscrepancy(double matrA[][], double vectorB[]){
        discrepancy = new double[n];
        double[] res = new double[n];
        for(int i = 0; i < n; i++){
            res[i] = 0;
            for(int j = 0; j < n; j++){
                res[i] += matrA[i][j]*vectorX[j];
            }
            discrepancy[i] = vectorB[i] - res[i];
        }
    }

    public void printVectorX(){
        System.out.println("Vector X");
        for(double item: vectorX){
            System.out.format("%25s", item + "    ");
        }
        System.out.println();
    }
    public void printDiscrepancy(){
        System.out.println("Discrepancy");
        for(double item: discrepancy){
            System.out.format("%25s", item + "    ");
        }
        System.out.println();
    }
    public void printMatr(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.format("%25s", matrA[i][j] + "    ");
            }
            System.out.format("%25s", vectorB[i] + "\n");
        }
        System.out.println();
    }

    public void prindDetA(){
        double det = 1;
        for(int i = 0; i < n; i++){
            det*= matrA[i][i];
        }
        System.out.println("\ndet = " + det);
    }
}
