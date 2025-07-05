package com.app.musiclover.service;

import com.app.musiclover.data.dao.MusicalPieceDao;
import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.MusicalPieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MusicalPieceServiceImpl implements MusicalPieceService {

    private final MusicalPieceDao musicalPieceDao;

    @Override
    public MusicalPiece createMusicalPiece(MusicalPiece musicalRequest) {
        return musicalPieceDao.save(musicalRequest);
    }

    @Override
    public MusicalPiece getMusicalPieceById(Long musicalPieceId) {
        return musicalPieceDao.findById(musicalPieceId)
                .orElseThrow(() -> new NotFoundException("Musical piece id: " + musicalPieceId));
    }

    @Override
    public List<MusicalPiece> getMusicalPiecesByTitleAndComposerAndEra(String title, String composer, String era) {
        return musicalPieceDao.searchByFilters(title, composer, era);
    }

    @Override
    public MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest) {
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        musicalPiece.update(musicalPieceRequest);
        return musicalPieceDao.save(musicalPiece);
    }

    @Override
    public void deleteMusicalPieceById(Long musicalPieceId) {
        if (!musicalPieceDao.existsById(musicalPieceId)) {
            throw new NotFoundException("Musical piece id: " + musicalPieceId);
        }
        musicalPieceDao.deleteById(musicalPieceId);
    }
}
