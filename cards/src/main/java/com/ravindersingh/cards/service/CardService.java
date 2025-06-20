package com.ravindersingh.cards.service;

import com.ravindersingh.cards.constant.CardConstant;
import com.ravindersingh.cards.dto.CardDto;
import com.ravindersingh.cards.entity.Card;
import com.ravindersingh.cards.exception.CardAlreadyExistsException;
import com.ravindersingh.cards.exception.ResourceNotFoundException;
import com.ravindersingh.cards.mapper.CardMapper;
import com.ravindersingh.cards.repository.CardRepository;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;

    public void createCard(String mobileNumber) {
        Optional<Card> card = cardRepository.findByMobileNumber(mobileNumber);
        if( card.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobile number : " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstant.CREDIT_CARD);
        newCard.setTotalLimit(CardConstant.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstant.NEW_CARD_LIMIT);
        return newCard;
    }

    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        CardMapper.mapToCard(cardDto, card);
        cardRepository.save(card);
        return true;
    }

    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardRepository.deleteById(cards.getCardId());
        return true;
    }


}
