package ru.itis.cinema.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.cinema.repositories.CustomerRepository;

@RequiredArgsConstructor
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomerUserDetails(
                customerRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User not found")));
    }
}
