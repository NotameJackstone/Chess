package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    //for tile number / only set at construction time which is why final is used
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleTiles();

    private static Map<Integer,EmptyTile> createAllPossibleTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new occupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }//end constructor

    //wheather or not the given tile is occupied or not
    public abstract boolean isTileOccupied();

    //get the piece off a given tile
    public abstract Piece getPiece();
//-------------------------------------------------------------------------------------
    public static final class EmptyTile extends Tile{

        EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }//end static final class
//-----------------------------------------------------------------------------------
    public static final class occupiedTile extends Tile{

        private final Piece pieceOnTile;

        occupiedTile(int tileCoordinate,final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString(){
            try{
                return getPiece().getPieceAlliance().isBlack() ? toString().toLowerCase() : getPiece().toString();
                //return "#";
            }catch (NullPointerException e){
                System.out.println(e.toString());
            }
            return "8";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }//end of static final class
}//end the whole Tile class
