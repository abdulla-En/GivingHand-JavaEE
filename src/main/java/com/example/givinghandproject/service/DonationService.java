package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.CampaignItemDAO;
import com.example.givinghandproject.dao.DonationDAO;
import com.example.givinghandproject.dao.UserDAO;
import com.example.givinghandproject.dto.donation.DonationRequestDTO;
import com.example.givinghandproject.entity.CampaignItem;
import com.example.givinghandproject.entity.Donation;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.utilities.enums.DonationStatus;
import com.example.givinghandproject.utilities.enums.UserType;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class DonationService {
    @Inject private DonationDAO donationDAO;
    @Inject private CampaignItemDAO ciDAO;
    @Inject
    private UserDAO userDAO;

    public Long commitDonation(DonationRequestDTO dto, String email) {
        User donor = userDAO.getByEmail(email)
                .orElseThrow(() -> new BusinessException("Auth", "Donor not found"));

        CampaignItem ci = ciDAO.findById(dto.getCampaignItemId());
        if (ci == null) throw new BusinessException("Item", "Campaign item not found");

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setCampaignItem(ci);
        donation.setQuantity(dto.getQuantity());
        donation.setStatus(DonationStatus.COMMITTED);

        donationDAO.create(donation);
        return donation.getId();
    }

    public void updateCommitment(Long id, int newQuantity, User currentUser) {
        Donation donation = findAndCheckOwnership(id, currentUser);

        if (donation.getStatus() != DonationStatus.COMMITTED) {
            throw new BusinessException("Status", "Cannot edit; donation already received or distributed.");
        }

        donation.setQuantity(newQuantity);
        donationDAO.update(donation);
    }

    public void cancelCommitment(Long id, User currentUser) {
        Donation donation = findAndCheckOwnership(id, currentUser);

        if (donation.getStatus() != DonationStatus.COMMITTED) {
            throw new BusinessException("Status", "Cannot cancel; donation already received.");
        }

        donationDAO.delete(donation);
    }

    public void markAsReceived(Long id) {
        Donation donation = donationDAO.findById(id);
        if (donation.getStatus() != DonationStatus.COMMITTED) {
            throw new BusinessException("Status", "Only committed donations can be marked as received.");
        }

        donation.setStatus(DonationStatus.RECEIVED);

        CampaignItem ci = donation.getCampaignItem();
        ci.setReceivedQuantity(ci.getReceivedQuantity() + donation.getQuantity());

        donationDAO.update(donation);
        ciDAO.update(ci);
    }

    public void markAsDistributed(Long id) {
        Donation donation = donationDAO.findById(id);
        if (donation.getStatus() != DonationStatus.RECEIVED) {
            throw new BusinessException("Status", "Donation must be RECEIVED before distribution.");
        }

        donation.setStatus(DonationStatus.DISTRIBUTED);

        User donor = donation.getDonor();
        String logEntry = "Distributed " + donation.getQuantity() + " of " +
                donation.getCampaignItem().getItem().getName();
        donor.getDonationLogHistory().add(logEntry);

        donationDAO.update(donation);
        userDAO.update(donor);
    }

    private Donation findAndCheckOwnership(Long id, User user) {
        Donation d = donationDAO.findById(id);
        if (d == null) throw new BusinessException("Not Found", "Donation not found");
        if (!d.getDonor().getId().equals(user.getId()) && user.getRole() != UserType.Admin) {
            throw new BusinessException("Security", "This is not your donation to manage!");
        }
        return d;
    }
}
