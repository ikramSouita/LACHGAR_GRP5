package com.example.app.services;

import com.example.app.dtos.ObjetSuiviDTO;
import com.example.app.entities.ObjetSuivi;
import com.example.app.repositories.ObjetSuiviRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjetSuiviService {

    @Autowired
    private ObjetSuiviRepository objetSuiviRepository;



    // Ajouter un nouvel objet suivi
    public ObjetSuiviDTO addObjetSuivi(ObjetSuiviDTO objetSuiviDTO) {
        ObjetSuivi objetSuivi = new ObjetSuivi();
        objetSuivi.setNomObjet(objetSuiviDTO.getNomObjet());
        objetSuivi.setTypeObjet(objetSuiviDTO.getTypeObjet());

        // Enregistrer l'objet suivi dans la base de données
        objetSuiviRepository.save(objetSuivi);

        // Mettre à jour l'ID dans le DTO et le retourner
        objetSuiviDTO.setId(objetSuivi.getId());
        return objetSuiviDTO;
    }



    // Mettre à jour un objet suivi
    public ObjetSuiviDTO updateObjetSuivi(Long id, ObjetSuiviDTO objetSuiviDTO) {
        Optional<ObjetSuivi> objetOpt = objetSuiviRepository.findById(id);
        if (objetOpt.isPresent()) {
            ObjetSuivi objetSuivi = objetOpt.get();
            objetSuivi.setNomObjet(objetSuiviDTO.getNomObjet());
            objetSuivi.setTypeObjet(objetSuiviDTO.getTypeObjet());
            objetSuiviRepository.save(objetSuivi);
            return objetSuiviDTO;
        }
        return null;
    }

    // Supprimer un objet suivi
    public boolean deleteObjetSuivi(Long id) {
        if (objetSuiviRepository.existsById(id)) {
            objetSuiviRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public ObjetSuiviDTO getObjetSuivi(Long id) {
        Optional<ObjetSuivi> objetOpt = objetSuiviRepository.findById(id);
        if (objetOpt.isPresent()) {
            ObjetSuivi objetSuivi = objetOpt.get();
            return new ObjetSuiviDTO( objetSuivi.getId(),objetSuivi.getNomObjet(), objetSuivi.getTypeObjet());
        }
        return null;
    }

}
