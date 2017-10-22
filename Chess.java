import java.util.Scanner;
public class Chess {
    Piece[][] pieces=new Piece[8][8];
    String[][] board=new String[8][8];
    boolean redraw=true;
    public void init(){
        boolean ifBlack=false;
        if(redraw){
        for(int r=0;r<=7;r++){
            if(r==7||r==6)
                ifBlack=true;
            for(int c=0;c<=7;c++){
                if(r==6||r==1){ //creates pawns 
                   pieces[r][c]=new Pawn();
                   pieces[r][c].setInfo(r, c,ifBlack);
                   board[r][c]=getColor(ifBlack)+"P";
                }
                else if((r==7||r==0)&&(c==0||c==7)){ //creates rooks aka elephant
                    pieces[r][c]=new Rook();
                    pieces[r][c].setInfo(r, c, ifBlack);
                    board[r][c]=getColor(ifBlack)+"R";
                }
                else if((r==7||r==0)&&(c==1||c==6)){ //creates knights
                    pieces[r][c]=new Knight();
                    pieces[r][c].setInfo(r, c, ifBlack);
                    board[r][c]=getColor(ifBlack)+"k";
                }
                else if((r==7||r==0)&&(c==2||c==5)){ //creates bishop
                   pieces[r][c]=new Bishop();
                   pieces[r][c].setInfo(r, c, ifBlack);
                   board[r][c]=getColor(ifBlack)+"B";
               }
            }
        }
               pieces[0][3]=new Queen();
               pieces[0][3].setInfo(0, 3, false);
               board[0][3]=getColor(false)+"Q";
               
               pieces[0][4]=new King();
               pieces[0][4].setInfo(0, 4, false);
               board[0][4]=getColor(false)+"K";
               
               pieces[7][3]=new Queen();
               pieces[7][3].setInfo(7, 3, true);
               board[7][3]=getColor(true)+"Q";
               
               pieces[7][4]=new King();
               pieces[7][4].setInfo(7, 4, true);
               board[7][4]=getColor(true)+"K";
        }      
    }
    /**
     * creates the most ugly looking chess board
     * can be eliminated in GUI version
     */
    public void printBoard(){
        init();
        System.out.println("wk->white kight"
                + ",wK->white King");
        System.out.println("  0  1  2  3  4  5  6  7  ");
        for(int r=7;r>=0;r--){ //the loop is done in reverse beacause printing it the normal way would cause the white piece to be at the top
            System.out.print(r+" ");
            for(int c=0;c<=7;c++){
                if(board[r][c]!=null)
                System.out.print(board[r][c]+" ");
                else
                    System.out.print("   ");
            }
            System.out.println();
        }        
    }
    /**
     * Used intensively in creating the two dimensional array which is printed
     * in the game.(not a part of logic)
     * @param black color of the piece in boolean
     * @return the color of the piece as a character
     */
    private char getColor(boolean black){
        if(black)
            return 'B';
        else
            return 'W';
    }
    private boolean diagonalBlock(Piece p,int movedRow,int movedCol){
        boolean result=false;
        int cCol=p.column;
        int cRow=p.row;
        Piece target=pieces[movedRow][movedCol];
        String verDirection;
        String horDirection;
        if(movedRow>cRow){
            verDirection="forward";
            cRow++;
        }
        else{ 
            verDirection="backward";
            cRow--; 
        }
        if(movedCol>cCol){
            horDirection="right";
            cCol++;
        }
        else{
            horDirection="left";
            cCol--;
        }        
        while((cRow<=movedRow&&verDirection.equals("forward"))||(cRow>=movedRow&&verDirection.equals("backward"))){
            while((cCol<=movedCol&&horDirection.equals("right"))||(cCol>=movedCol&&horDirection.equals("left"))){
            if(pieces[cRow][cCol]!=target&&pieces[cRow][cCol]!=null){
                 result=true;
                 break;
            } 
              
            if(horDirection.equals("right")){
                cCol++;
                break;
            }
            else if(horDirection.equals("left")){
                cCol--; 
                break;
            }               
            }
            if(verDirection.equals("forward"))
                cRow++;
            else if(verDirection.equals("backward"))
                cRow--;            
        }
        return result;
    }
    private boolean horBlock(Piece p,int movedRow,int movedCol){
        boolean result=false;
        int cCol=p.column;
        Piece target=pieces[movedRow][movedCol];
        String direction;
        if(movedCol>cCol){
            direction="right";
            cCol++;
        }
        else{
            direction="left";
            cCol--;
        }
        while((cCol<=movedCol&&direction.equals("right"))||(cCol>=movedCol&&direction.equals("left"))){
            if(pieces[p.row][cCol]!=target&&pieces[p.row][cCol]!=null)
                result=true;
            
            if(direction.equals("right"))
                cCol++;
            else if(direction.equals("left"))
                cCol--;
        }
        return result;        
    }
    private boolean verBlock(Piece p,int movedRow,int movedCol){
        boolean result=false;
        String direction;
        Piece target=pieces[movedRow][movedCol];
        int cRow=p.row;
        if(movedRow>cRow){
            direction="forward";
            cRow++;
        }
        else{ 
            direction="backward";
            cRow--;
        }
        while((cRow<=movedRow&&direction.equals("forward"))||(cRow>=movedRow&&direction.equals("backward"))){
            if(pieces[cRow][p.column]!=target&&pieces[cRow][p.column]!=null)
                result=true;
            
            if(direction.equals("forward"))
                cRow++;
            else if(direction.equals("backward"))
                cRow--;
        }
        return result;
    }
    private boolean isVertical(Piece p,int movedRow,int movedColumn){
        int cRow=p.row;
        int cColumn=p.column;
        return cRow!=movedRow&&movedColumn==cColumn;
    }
    private boolean isSideways(Piece p,int moveRow,int movedColumn){
        int cRow=p.row;
        int cColumn=p.column;
        return cRow==moveRow&&cColumn!=movedColumn;
    }
    private boolean isDiagonal(Piece p,int movedRow,int movedColumn){
        int row=p.row;
        int column=p.column;
        boolean legal=false;
        int copiedCol=column;
        { //diagonally upward check 
            for(int r=row;r<=7;r++){  //assuming piece has moved diagonally right
            if(movedRow==r&&movedColumn==copiedCol)
                legal=true;
                copiedCol--;

        }
        copiedCol=column;
        for(int r=row;r<=7;r++){  //assuming piece has moved diagonally left
            if(movedRow==r&&movedColumn==copiedCol)
                legal=true;
                copiedCol++;
            }
          }           
         { //diagonally downward check
             copiedCol=column;
            for(int r=row;r>=0;r--){  //assuming piece has moved diagonally right 
                if(movedRow==r&&movedColumn==copiedCol)
                legal=true;
                copiedCol--;
            }
            copiedCol=column;
            for(int r=row;r>=0;r--){  //assuming piece has moved diagonally left
                 if(movedRow==r&&movedColumn==copiedCol)
                 legal=true;
                 copiedCol++;
            }
         }
             return legal;
    }
    private abstract class Piece{
        int row;
        int column;
        /**
         * true when this piece is black
         */
        boolean black;
          void setInfo(int r,int c,boolean b){
           this.row=r;
           this.column=c;
           this.black=b;
          }
         abstract boolean move(int movedRow,int movedColumn);
    }
    private class King extends Piece{           
        @Override
         boolean move(int movedRow,int movedColumn){
            boolean result=false;  
            if(isVertical(this,movedRow,movedColumn)){
                if(movedRow==row-1||movedRow==row+1)
                    result=true;
            }
            else if(isSideways(this,movedRow,movedColumn)){
                if((movedColumn==column-1||movedColumn==column+1))
                    result=true;
            }
            else if(isDiagonal(this,movedRow,movedColumn)){
                if((movedRow==row+1||movedRow==row-1)&&(movedColumn==column+1||movedColumn==column-1))
                    result=true;
            }
            return result;
        }
    }
    private class Queen extends Piece{           
         boolean move(int movedRow,int movedColumn){
            boolean result=false;
            if(isVertical(this,movedRow,movedColumn)&&verBlock(this,movedRow,movedColumn)==false)
                result=true;
            else if(isSideways(this,movedRow,movedColumn)&&horBlock(this,movedRow,movedColumn)==false)
                result=true;
            else if(isDiagonal(this,movedRow,movedColumn)&&diagonalBlock(this,movedRow,movedColumn)==false)
                result=true;
            return result;
        }
        
    }
    private class Pawn extends Piece{ 
        int squareMoved=0; //if it's zero it means this piece hasn't moved yet , and it can move maximum 2 squares
        @Override
         boolean move(int movedRow,int movedColumn){
             boolean result=false;
                if(squareMoved==0){ //it is moving first time
                    boolean b=isVertical(this,movedRow,movedColumn);
                    boolean b1=movedRow<=3&&black==false;
                    boolean b2=movedRow>=4&&black;
                    result = b&&(b2||b1); //it should not go further than square 
                   //3 if it's a white piece and it should not go further than square number 4 if it's a black piece
                    if(isDiagonal(this,movedRow,movedColumn)&&pieces[row][column]!=null)
                        result=true;
                    squareMoved++;
                }
                else { //it has moved before 
                    if(isVertical(this,movedRow,movedColumn)){
                        if(movedRow==row+1&&black==false)
                        result=true;
                        else if(movedRow==row-1&&black)
                        result=true;    
                    }
                    else if(isDiagonal(this,movedRow,movedColumn)&&pieces[row][column]!=null){
                        if(black!=pieces[movedRow][movedColumn].black&&(movedColumn==column+1||movedColumn==column-1))
                        result=true;   
                    }
                }
            return result;    
        }         
    }
    private class Knight extends Piece{
         boolean move(int movedRow,int movedColumn){
            return (movedRow==row+2||movedRow==row-2)&&(movedColumn==column+1||movedColumn==column-1);
       }         
    }
    private class Bishop extends Piece{  // aka camel
         boolean move(int movedRow,int movedColumn){
            return isDiagonal(this,movedRow,movedColumn)&&diagonalBlock(this,movedRow,movedColumn)==false;
        }         
    }
    private class Rook extends Piece{ //aka elephant 
        @Override
         boolean move(int movedRow,int movedColumn){
             boolean result=false;
             if(isVertical(this,movedRow,movedColumn)&&verBlock(this,movedRow,movedColumn)==false)
                 result=true;
             else if(isSideways(this,movedRow,movedColumn)&&horBlock(this,movedRow,movedColumn)==false)
                 result=true;
            return result;
        }           
    }
    /**
     * Change print statements to something else when converting this program to
     * GUI
     * @param black true when black has to move false when it's white's move
     */
    private void play(boolean black){
        Scanner stdin=new Scanner(System.in);
        printBoard();
        System.out.print("->");
        int blackAlive=0;
        int whiteAlive=0;
        redraw=false;
        for(int r=0;r<=7;r++){ //counting the pieces which are alive
            for(int c=0;c<=7;c++){
                if(pieces[r][c]!=null &&!(pieces[r][c] instanceof King)){
                    if(pieces[r][c].black)
                        blackAlive++;
                    else
                        whiteAlive++;
                }
            }
        }
        try{
        if(blackAlive==0){
            System.out.println("White side wins");
            return;
        }
        else if(whiteAlive==0){
            System.out.println("Black side wins ");
            return;
        }
        int row=stdin.nextInt();
        int column=stdin.nextInt();
        int newRow=stdin.nextInt();
        int newCol=stdin.nextInt();		
        if(pieces[row][column].black!=black){
            System.out.println("Select your piece not opponent's ");
            play(black);
        }
        else{
            if(pieces[row][column].move(newRow, newCol)){
                if(pieces[newRow][newCol]==null||pieces[newRow][newCol].black!=black){
                    Piece temp=pieces[row][column];
                    String tempS=board[row][column];
                    boolean isKing=false;
                    if(pieces[newRow][newCol]!=null){
                        if(pieces[newRow][newCol] instanceof King&&pieces[newRow][newCol].black){
		            System.out.println("White side wins.");
                            isKing=true;
                        }
                        else if(pieces[newRow][newCol] instanceof King && pieces[newRow][newCol].black==false){
                            System.out.println("Black side wins.");
                            isKing=true;
                        }
                        if(pieces[newRow][newCol].black)
                            blackAlive--;
                        else
                            whiteAlive--;
                    }
                    pieces[row][column]=null;
                    pieces[newRow][newCol]=temp;
                    pieces[newRow][newCol].setInfo(newRow, newCol, black);
                    board[row][column]=null;
                    board[newRow][newCol]=tempS;
                    if(!isKing)
                    play(!black);
                    else
                    printBoard();    
                }
                else{
                    System.out.println("Invalid move,the square is occupied by any of your own piece ");
                    play(black);
                }
            }
            else{
                System.out.println("Invalid Move");
                play(black);
            }
        }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Please provide valid Co-ordinates");
            play(black);
        }
        catch(NullPointerException e){
            System.out.println("There is no piece on your selcted location.");
            play(black);
        }
    } 
    public static void main(String args[]){
        Chess game=new Chess();
        game.init();
        game.play(false);
    }
} 