package ru.itis.cinema.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.cinema.dto.SignUpDto;
import ru.itis.cinema.models.Customer;
import ru.itis.cinema.repositories.CustomerRepository;
import ru.itis.cinema.services.SignUpService;
import ru.itis.cinema.util.EmailUtil;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;

import static ru.itis.cinema.models.Customer.State.CONFIRMED;
import static ru.itis.cinema.models.Customer.State.NOT_CONFIRMED;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final CustomerRepository customerRepository;

    private final EmailUtil emailUtil;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(SignUpDto signUpDto) {
        Customer customer = Customer.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .state(NOT_CONFIRMED)
                .role(Customer.Role.USER)
                .confirmCode(UUID.randomUUID().toString())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        customerRepository.save(customer);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("confirm_code", customer.getConfirmCode());
        data.put("username", customer.getFirstName() + " " + customer.getLastName());
        emailUtil.sendMail(customer.getEmail(), "confirm", "confirm_mail.ftlh",
                data);

    }

    @Transactional
    @Override
    public void confirm(String confirmCode) {
        Customer customer = customerRepository.getCustomerByConfirmCode(confirmCode).orElseThrow();
        if (customer.getState().equals(NOT_CONFIRMED)) {
            customer.setState(CONFIRMED);
            customerRepository.save(customer);
        }
    }

}
