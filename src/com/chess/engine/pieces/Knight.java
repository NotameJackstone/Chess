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

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        //int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        //in the video its currentCandidateOffset
        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES){
           int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                   isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
                   isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
                   isEigthColumnExclusion(this.piecePosition, currentCandidate)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }//end if
                }
            }//end if
        }//end for loop
        return ImmutableList.copyOf(legalMoves);
    }//end method

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    public static boolean isFirstColumnExclusion(final int CurrentPositionm, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[CurrentPositionm] && ((candidateOffset == -17) || (candidateOffset == -10) ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset  == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset  == -6 || candidateOffset == 10);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset  == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }

}//end knight class
