from pprint import pprint as pp
from math import sqrt
def risolvi_sudoku(schema):

    def get_i_j(i, j):
        """
            Restituisce le prossime cordinate per la ricorsione
        """
        return (i, j+1) if j < N-1 else (i+1, 0)

    def get_blocco(i, j):
        """
            Restituisce gli indici del blocco (identifico un blocco come le cordinate della cella in alto
            a sinistra).
        """
        return i // rad_n , j // rad_n

    N = len(schema)
    rad_n = int(sqrt(N))
    schema_copia = copy(schema)
    gia_inseriti = 0
    righe = {x:set() for x in range(N)}
    colonne = {x:set() for x in range(N)}
    blocco = dict()
    for y in range(rad_n):
        for x in range(rad_n):
            blocco[(y,x)] = set()
    for y in range(N):
        righe[y] = set(schema[y])
        for x in range(N):
            if schema[y][x] != 0: gia_inseriti += 1
            colonne[x].add(schema[y][x])
            blocco[get_blocco(y, x)].add(schema[y][x])

    def n_valido(schema, n, i, j):
        """
            Restituisce True se il numero n, nello schema nella posizione (i,j)
            e' possibile inserirlo
        """
        if n in righe[i] or n in colonne[j]: return False
        if n in blocco[get_blocco(i, j)]: return False
        return True
    def risolvi(schema, gia_inseriti, i, j):
        """
            Funzione che risolve un sudoku N*N
        """
        # se ha N*N elementi --> il sudoku e' finito
        if gia_inseriti == (N*N): return schema
        # se la posizione i, j non e' vuota allora passa alla cella dopo
        if schema[i][j] != 0:
            # calcola i nuovi indici
            new_i, new_j = get_i_j(i, j)
            sol = risolvi(schema, gia_inseriti, new_i, new_j)
            #se il sottoalbero genera soluzione restituiscila
            if sol: return sol
        else:
            # per ogni n' in [1,N]
            for n in range(1, N+1):
                # se il numero e' valido (ovvero non è presente sulla riga i, o sulla colonna j o nel blocco)
                if n_valido(schema, n, i, j):
                    # metti il numero e fai una ricorsione su di esso
                    schema[i][j] = n
                    # calcola gli indici della prossima cella
                    new_i, new_j = get_i_j(i, j)
                    # aggiungi l'elemento agli elementi della colonna[j], riga[i] e il blocco
                    v_blk, h_blk = get_blocco(i, j)
                    colonne[j].add(n)
                    righe[i].add(n)
                    blocco[(v_blk, h_blk)].add(n)
                    # scendi nel sottoalbero
                    sol = risolvi(schema, gia_inseriti+1, new_i, new_j)
                    # se genera una soluzione allora restituiscila
                    if sol: return sol
                    # se non genera soluzione, passa al prossimo n'
                    colonne[j].remove(n)
                    righe[i].remove(n)
                    blocco[(v_blk, h_blk)].remove(n)
                    schema[i][j] = schema_copia[i][j]
        return None
    printColored("red", "La soluzione al sudoku e':")
    pp(risolvi(schema, gia_inseriti, 0, 0))

def printColored(color, *msg):
    tc = 0
    s = "".join([str(m) for m in msg])
    
    if color == "red": tc = '\033[31m'
    elif color == "green": tc = '\033[32m'
    elif color == "blue": tc = '\033[34m'
    elif color == "white": tc = '\033[m'
    elif color == "cyan": tc = '\033[36m'
    print(tc, s, '\033[m')

def copy(mat):
    """
        Funzione che data una matrice restituisce una deepcopy
        (e' piu veloce di copy.deepcopy(mat))
    """
    newmat = [[] for _ in range(len(mat))]
    for i in range(len(mat)):
        for j in range(len(mat[0])):
            newmat[i].append(mat[i][j])
    return newmat

schema = [
    [0,0,5,3,0,0,0,0,0],
    [8,0,0,0,0,0,0,2,0],
    [0,7,0,0,1,0,5,0,0],
    [4,0,0,0,0,5,3,0,0],
    [0,1,0,0,7,0,0,0,6],
    [0,0,3,2,0,0,0,8,0],
    [0,6,0,5,0,0,0,0,9],
    [0,0,4,0,0,0,0,3,0],
    [0,0,0,0,0,9,7,0,0]
]

schema16 = [
    [0,0,5,0,4,8,0,0,0,0,13,12,0,2,0,0],
    [0,0,0,0,3,5,15,14,11,2,10,1,0,0,0,0],
    [1,0,4,6,0,0,0,0,0,0,0,0,8,7,0,10],
    [0,0,2,3,0,10,0,0,0,0,5,0,11,15,0,0],
    [2,12,0,0,0,0,6,11,10,15,0,0,0,0,4,16],
    [6,3,0,14,0,0,0,0,0,0,0,0,9,0,10,7],
    [0,10,0,0,9,0,2,8,3,1,0,7,0,0,12,0],
    [0,4,0,0,10,0,3,0,0,16,0,6,0,0,8,0],
    [0,5,0,0,6,0,1,0,0,11,0,15,0,0,16,0],
    [0,6,0,0,12,0,8,16,5,4,0,9,0,0,2,0],
    [16,9,0,2,0,0,0,0,0,0,0,0,13,0,11,14],
    [10,15,0,0,0,0,14,3,16,13,0,0,0,0,1,6],
    [0,0,6,7,0,3,0,0,0,0,11,0,14,8,0,0],
    [12,0,3,16,0,0,0,0,0,0,0,0,2,4,0,5],
    [0,0,0,0,14,15,13,12,8,7,9,3,0,0,0,0],
    [0,0,10,0,8,6,0,0,0,0,1,4,0,13,0,0],
]

schema16_2 = [
    [15,16,5,0,4,8,11,6,0,0,13,12,0,2,0,0],
    [0,0,0,0,3,5,15,14,11,2,10,1,16,6,13,4],
    [1,0,4,6,2,12,0,0,0,0,0,0,8,7,0,10],
    [0,0,2,3,0,10,0,0,0,0,5,0,11,15,0,0],
    [2,12,0,0,0,0,6,11,10,15,0,0,0,0,4,16],
    [6,3,0,14,15,1,12,4,0,0,0,0,9,0,10,7],
    [0,10,0,0,9,0,2,8,3,1,0,7,0,0,12,0],
    [0,4,0,0,10,0,3,0,0,16,0,6,0,0,8,0],
    [0,5,0,0,6,0,1,0,0,11,0,15,0,0,16,0],
    [0,6,0,0,12,0,8,16,5,4,0,9,0,0,2,0],
    [16,9,0,2,0,0,0,0,0,0,0,0,13,0,11,14],
    [10,15,7,4,0,0,14,3,16,13,0,0,0,0,1,6],
    [0,0,6,7,0,3,0,0,0,0,11,0,14,8,0,0],
    [12,8,3,16,1,11,10,9,0,0,0,0,2,4,0,5],
    [0,0,0,0,14,15,13,12,8,7,9,3,0,0,0,0],
    [0,0,10,0,8,6,0,0,0,0,1,4,12,13,0,0],
]

if __name__ == "__main__":
    risolvi_sudoku(schema16_2)