package com.example.watch.service;

import com.example.watch.dto.request.*;
import com.example.watch.dto.response.AuthResponseDTO;
import com.example.watch.dto.response.UserResponseDTO;
import com.example.watch.entity.PasswordResetOtp;
import com.example.watch.entity.User;
import com.example.watch.enums.UserRole;
import com.example.watch.enums.UserStatus;
import com.example.watch.repository.PasswordResetOtpRepository;
import com.example.watch.repository.UserRepository;
import com.example.watch.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    public AuthService(
            UserRepository userRepository,
            PasswordResetOtpRepository otpRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            MailService mailService) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
    }

    // ===================== REGISTER =====================
    public AuthResponseDTO register(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token, toUserResponse(user));
    }

    // ===================== LOGIN =====================
    public AuthResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()));

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token, toUserResponse(user));
    }

    // ===================== FORGOT PASSWORD =====================
    public void forgotPassword(ForgotPasswordRequestDTO dto) {

        // check email exists
        userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        // generate otp 6 digits
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        PasswordResetOtp reset = new PasswordResetOtp();
        reset.setEmail(dto.getEmail());
        reset.setOtp(otp);
        reset.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        reset.setUsed(false);

        otpRepository.save(reset);

        // send gmail real OTP
        mailService.sendOtp(dto.getEmail(), otp);
    }

    // ===================== VERIFY OTP ONLY =====================
    public void verifyOtp(VerifyOtpRequestDTO dto) {

        PasswordResetOtp otp = otpRepository
                .findTopByEmailOrderByIdDesc(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP không tồn tại"));

        if (otp.isUsed()) {
            throw new RuntimeException("OTP đã được sử dụng");
        }

        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP đã hết hạn");
        }

        if (!otp.getOtp().equals(dto.getOtp())) {
            throw new RuntimeException("OTP không đúng");
        }

        // chỉ xác thực OTP – không đổi mật khẩu tại đây
    }

    public void resetPassword(ResetPasswordRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // ===================== MAPPER =====================
    private UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus());
    }
}
