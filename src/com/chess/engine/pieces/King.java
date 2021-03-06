package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public King(Alliance pieceAlliance, int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
          final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

          if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
             isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
              continue;
          }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){}
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
        }//end for loop
        return ImmutableList.copyOf(legalMoves);
    }//end method

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    public static boolean isFirstColumnExclusion(final int CurrentPositionm, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[CurrentPositionm] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }//end method

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }//end method
}
