package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Bishop extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
                int candidateDestinationCoordinate = this.piecePosition;
                    while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                        if(isFirstColumnExclusion(this.piecePosition, candidateCoordinateOffset) ||
                                isEigthColumnExclusion(this.piecePosition, candidateCoordinateOffset)){
                            break;
                        }

                        candidateDestinationCoordinate += candidateCoordinateOffset;

                        if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                            final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                            if(!candidateDestinationTile.isTileOccupied()){
                                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                            }else{
                                final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                                final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                                if(this.pieceAlliance != pieceAlliance){
                                    legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                                }//end if
                            }//end else
                            break;
                        }//end if
                    }//end while loop
        }//end for loop
        return ImmutableList.copyOf(legalMoves);
    }//end method

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    public static boolean isFirstColumnExclusion(final int CurrentPositionm, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[CurrentPositionm] && (candidateOffset == -9) || (candidateOffset == 7);
    }//end method

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset  == -7) || (candidateOffset == 9);
    }//end of method
}
