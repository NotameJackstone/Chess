package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }//end method

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                //MORE WORK TO DO THERE
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            }else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())){
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                    !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }//end if
                else if(currentCandidateOffset == 7 &&
                        (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                        (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite())){
                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        //TO DO MORE WORK HERE
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
                }else if(currentCandidateOffset == 9 &&
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite())){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    //TO DO MORE WORK HERE
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }//end if
            }//end else if

                }//end for loop
        return ImmutableList.copyOf(legalMoves);
            }//end method

    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }

        }//end class
