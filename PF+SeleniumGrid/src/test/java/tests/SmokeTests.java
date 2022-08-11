package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SmokeTests extends BaseTest {


    private static final long DEFAULT_WAITING_TIME = 90;

    @Test
    public void checkMainComponentsOnHomePage() {
        getHomePage().waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        assertTrue(getHomePage().isHeaderVisible());
        assertTrue(getHomePage().isFooterVisible());
        assertTrue(getHomePage().isSearchFieldVisible());
        assertTrue(getHomePage().isCartIconVisible());
        assertTrue(getHomePage().getLanguageButtonText().equalsIgnoreCase("Français"));
        assertTrue(getHomePage().isRegisterButtonVisible());
        assertTrue(getHomePage().isSignInButtonVisible());
        getHomePage().clickSignInButton();
        getHomePage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getHomePage().getSignInPopup());
        assertTrue(getHomePage().isEmailFieldVisible());
        assertTrue(getHomePage().isPasswordFieldVisible());
        getHomePage().clickSignInPopupCloseButton();
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        getHomePage().clickStoreButton();
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        assertTrue(getHomePage().isStorePopupVisible());
        getHomePage().clickCartButton();
        getHomePage().waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getShoppingCartPage().getShoppingCartTitle());
        assertTrue(getShoppingCartPage().isShoppingCartTitleVisible());
        getHomePage().clickLanguageButton();
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        assertTrue(getDriver().getCurrentUrl().contains("fr"));
    }

    @Test
    public void checkWishList() throws InterruptedException {
        getHomePage().waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        getHomePage().isSearchFieldVisible();
        getHomePage().enterTextToSearchField("cake");
        Thread.sleep(30000);//потому что баг, а не потому что так можно делать особенно вам!!!
        getSearchResultsPage().clickWishListOnFirstProduct();
        getHomePage().waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getHomePage().getWishListProductsCount());
        assertEquals(getHomePage().getAmountOfProductsInWishList(), "1");
    }

    @Test
    public void checkAddToCart() {
        getHomePage().waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        getHomePage().waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        getHomePage().isSearchFieldVisible();
        getHomePage().enterTextToSearchField("0830187p");
        getProductPage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getProductPage().getAddToCartButton());
        getProductPage().clickAddToCartButton();
        getProductPage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getProductPage().getAddToCartPopupHeader());
        assertTrue(getProductPage().isAddToCartPopupVisible());
        getProductPage().isContinueShoppingButtonVisible();
        getProductPage().isContinueToCartButtonVisible();
        assertEquals(getProductPage().getAddToCartPopupHeaderText(), "You have added 1 item(s) to your cart");
        getProductPage().clickContinueToCartButton();
        getShoppingCartPage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getShoppingCartPage().getShoppingCartItem());
        getShoppingCartPage().clickCheckoutButton();
        getShoppingCartPage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getCheckoutPage().getPaymentCartButton());
        getCheckoutPage().clickPaymentCartButton();
        getShoppingCartPage().waitVisibilityOfElement(DEFAULT_WAITING_TIME, getCheckoutPage().getPaymentForm());
        assertTrue(getCheckoutPage().isPaymentFormVisible());
        assertTrue(getCheckoutPage().isBillingFormVisible());
        assertTrue(getCheckoutPage().isCompleteOrderButtonVisible());
    }
}
