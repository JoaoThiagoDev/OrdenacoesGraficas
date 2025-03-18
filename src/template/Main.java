package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {

    private int[] arrayInsertion;
    private int[] arraySelection;
    private int[] arrayBubble;
    private int[] arrayMerge;

    private List<int[]> listaSelection;
    private List<int[]> listaInsertion;
    private List<int[]> listaBubble;
    private List<int[]> listaMerge;

    private int posInsertion;
    private int posSelection;
    private int posBubble;
    private int posMerge;

    private double tempoParaMudar;
    private double contadorTempo;

    public Main() {
        super(800, 450, "Ordenações", 60, true);
    }

    @Override
    public void create() {
        arraySelection = new int[]{9, 10, 5, 6, 3, 1, 2, 8};
        arrayInsertion = new int[]{9, 10, 5, 6, 3, 1, 2, 8};
        arrayBubble = new int[]{9, 10, 5, 6, 3, 1, 2, 8};
        arrayMerge = new int[]{9, 10, 5, 6, 3, 1, 2, 8};

        listaSelection = new ArrayList<>();
        listaInsertion = new ArrayList<>();
        listaBubble = new ArrayList<>();
        listaMerge = new ArrayList<>();

        tempoParaMudar = 1;
        // teste

        copiarArray(listaSelection, arraySelection);
        copiarArray(listaInsertion, arrayInsertion);
        copiarArray(listaBubble, arrayBubble);
        copiarArray(listaMerge, arrayMerge);

        selectionSort(arraySelection);
        insertionSort(arrayInsertion);
        bubbleSort(arrayBubble);
        mergeSort(arrayMerge, 0, arrayMerge.length - 1);
    }

    @Override
    public void update(double delta) {
        contadorTempo += delta;

        if (contadorTempo > tempoParaMudar) {
            contadorTempo = 0;
            if (posSelection < listaSelection.size() - 1) {
                posSelection++;
            }
            if (posInsertion < listaInsertion.size() - 1) {
                posInsertion++;
            }
            if (posBubble < listaBubble.size() - 1) {
                posBubble++;
            }
            if (posMerge < listaMerge.size() - 1) {
                posMerge++;
            }
        }

    }

    @Override
    public void draw() {

        clearBackground(WHITE);

        drawText("Selection Sort", 10, 10, 25, BLACK);
        drawText("Insertion Sort", getScreenWidth() / 2 + 10, 10, 25, BLACK);
        drawText("Bubble Sort", 10, getScreenHeight() / 2 + 10, 25, BLACK);
        drawText("Merge Sort", getScreenWidth() / 2 + 10, getScreenHeight() / 2 + 10, 25, BLACK);

        desenharArray(listaSelection.get(posSelection), 10, getScreenHeight() / 2 - 25, 30, 10, 10);
        desenharArray(listaInsertion.get(posInsertion), getScreenWidth() / 2 + 10, getScreenHeight() / 2 - 25, 30, 10, 10);
        desenharArray(listaBubble.get(posBubble), 10, getScreenHeight() - 10, 30, 10, 10);
        desenharArray(listaMerge.get(posMerge), getScreenWidth() / 2 + 10, getScreenHeight() - 10, 30, 10, 10);

    }

    private void copiarArray(List<int[]> lista, int[] array) {
        int[] novoArray = new int[array.length];
        System.arraycopy(array, 0, novoArray, 0, array.length);
        lista.add(novoArray);
    }

    private void desenharArray(int[] array, int x, int y, int largura, int espacamento, int tamanhoPedaco) {

        for (int i = 0; i < array.length; i++) {
            fillRectangle(
                    x + i * (largura + espacamento),
                    y - array[i] * tamanhoPedaco,
                    largura,
                    array[i] * tamanhoPedaco,
                    BLUE
            );
        }

    }

    private void selectionSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[menor]) {
                    menor = j;
                }
            }
            int t = array[i];
            array[i] = array[menor];
            array[menor] = t;
            copiarArray(listaSelection, array);
        }

    }

    private void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {

            int j = i;

            while (j > 0 && array[j] < array[j - 1]) {
                int aux = array[j];
                array[j] = array[j - 1];
                array[j - 1] = aux;
                j -= 1;
                copiarArray(listaInsertion, array);
                copiarArray(listaInsertion, array);
                j -= 1;
            }

        }
    }

    private void bubbleSort(int arr[]) {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                    copiarArray(listaBubble, arr);
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false) {
                break;
            }
        }
    }

    public void mergeSort(int[] v, int left, int right) {

        if (left >= right) {
            return;
        } else {

            int middle = (left + right) / 2;
            mergeSort(v, left, middle);
            mergeSort(v, middle + 1, right);

            merge(v, left, middle, right);
            
            copiarArray(listaMerge, v);
        }

    }

    public void merge(int[] v, int left, int middle, int right) {

        // transfere os elementos entre left e right para um array auxiliar.
        int[] helper = new int[v.length];
        for (int i = left; i <= right; i++) {
            helper[i] = v[i];
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {

            if (helper[i] <= helper[j]) {
                v[k] = helper[i];
                i++;
            } else {
                v[k] = helper[j];
                j++;
            }
            k++;

        }

        // se a metade inicial não foi toda consumida, faz o append.
        while (i <= middle) {
            v[k] = helper[i];
            i++;
            k++;
        }

        // Não precisamos nos preocupar se a metade final foi 
        // toda consumida, já que, se esse foi o caso, ela já está
        // no array final.
    }

    /**
     * Instancia a engine e a inicia.
     *
     * Instantiates the engine and starts it.
     */
    public static void main(String[] args) {
        new Main();
    }

}
