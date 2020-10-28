package com.netsuite.webservices.samples;

import com.netsuite.suitetalk.proxy.v2020_1.lists.relationships.Customer;
import com.netsuite.suitetalk.proxy.v2020_1.platform.core.RecordRef;
import com.netsuite.suitetalk.proxy.v2020_1.platform.messages.WriteResponse;
import com.netsuite.suitetalk.proxy.v2020_1.setup.customization.CustomRecord;
import com.netsuite.suitetalk.proxy.v2020_1.transactions.sales.SalesOrder;
import com.netsuite.suitetalk.proxy.v2020_1.transactions.sales.SalesOrderItem;
import com.netsuite.suitetalk.proxy.v2020_1.transactions.sales.Invoice;
import com.netsuite.suitetalk.proxy.v2020_1.transactions.sales.InvoiceItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.netsuite.webservices.samples.Messages.*;

/**
 * <p>This is type of map which converts particular objects to map of the fields. It can be simply printed
 * using {@link com.netsuite.webservices.samples.utils.PrintUtils#printMap(Map)} method.</p>
 * <p>© 2019 NetSuite Inc. All rights reserved.</p>
 */
@ParametersAreNonnullByDefault
public class Fields extends LinkedHashMap<String, String> {

    public Fields() {
        super();
    }

    public Fields(String internalId) {
        super();
        put(INTERNAL_ID, internalId);
    }

    public Fields(String internalId, String errorMessage) {
        this(internalId);
        put(ERROR, errorMessage);
    }

    public Fields(Customer customer) {
        super();
        put(INTERNAL_ID, customer.getInternalId());
        put(EXTERNAL_ID, customer.getExternalId());
        put(ENTITY_ID, customer.getEntityId());
        put(COMPANY_NAME, customer.getCompanyName());
        put(EMAIL, customer.getEmail());
        put(PHONE, customer.getPhone());
        put(ENTITY_STATUS, customer.getEntityStatus().getName());
        put(IS_INACTIVE, String.valueOf(customer.getIsInactive()));
        put(DATE_CREATED, customer.getDateCreated().getTime().toString());
    }

    public Fields(WriteResponse response, Customer customer) {
        super();
        put(INTERNAL_ID, ((RecordRef) response.getBaseRef()).getInternalId());
        put(EXTERNAL_ID, ((RecordRef) response.getBaseRef()).getExternalId());
        put(ENTITY_ID, customer.getEntityId());
        put(COMPANY_NAME, customer.getCompanyName());
        put(EMAIL, customer.getEmail());
        put(STATUS_INTERNAL_ID, getStatusKey(customer));
        put(ADDRESS_BOOK_LABEL, getAddressBookLabel(customer));
    }

    public Fields(Invoice invoice, boolean isEntityId) {
        super();
        put(INTERNAL_ID, invoice.getInternalId());
        if (isEntityId) {
            put(ENTITY_ID, invoice.getEntity().getInternalId());
        } else {
            put(CUSTOMER_NAME, invoice.getEntity().getName());
        }
        put(TRANSACTION_DATE, invoice.getTranDate().getTime().toString());
        put(CURRENCY_NAME, invoice.getCurrencyName());
        put(DUE_DATE, invoice.getDueDate().getTime().toString());
        put(START_DATE, invoice.getStartDate().getTime().toString());
        put(END_DATE, invoice.getEndDate() != null ? invoice.getEndDate().getTime().toString() : "");
        put(AMOUNT_PAID, String.valueOf(invoice.getAmountPaid() != null ? invoice.getAmountPaid() : 0));
        put(AMOUNT_REMAINING, String.valueOf(invoice.getAmountRemaining() != null ? invoice.getAmountRemaining() : 0));
        put(BALANCE, String.valueOf(invoice.getBalance() != null ? invoice.getBalance() : 0));
        put(TAX_TOTAL, String.valueOf(invoice.getTaxTotal() != null ? invoice.getTaxTotal() : 0));
        put(SUB_TOTAL, String.valueOf(invoice.getSubTotal() != null ? invoice.getSubTotal() : 0));
        put(TOTAL, String.valueOf(invoice.getTotal() != null ? invoice.getTotal() : 0));
    }

    public Fields(SalesOrder salesOrder, boolean isEntityId) {
        super();
        put(INTERNAL_ID, salesOrder.getInternalId());
        put(TRANSACTION_ID, salesOrder.getTranId());
        if (isEntityId) {
            put(ENTITY_ID, salesOrder.getEntity().getInternalId());
        } else {
            put(CUSTOMER_NAME, salesOrder.getEntity().getName());
        }
        put(TOTAL_AMOUNT, String.valueOf(salesOrder.getTotal()));
        put(DATE_CREATED, salesOrder.getCreatedDate().getTime().toString());
    }

    public Fields(SalesOrderItem item) {
        super();
        put(ITEM_NAME, item.getItem().getName());
        put(QUANTITY, String.valueOf(item.getQuantity()));
    }

    public Fields(InvoiceItem item) {
        super();
        put(ITEM_NAME, item.getItem().getName());
        put(QUANTITY, String.valueOf(item.getQuantity()));
        put(AMOUNT_REMAINING, String.valueOf(item.getAmount() != null ? item.getAmount() : 0));
    }

    public Fields(CustomRecord customRecord) {
        super();
        put(CUSTOM_RECORD_TYPE_ID, customRecord.getRecType().getInternalId());
        put(INTERNAL_ID, customRecord.getInternalId());
        put(NAME, customRecord.getName());
    }

    private static String getStatusKey(Customer customer) {
        if (customer.getEntityStatus() == null) {
            return null;
        }
        return customer.getEntityStatus().getInternalId();
    }

    private static String getAddressBookLabel(Customer customer) {
        final int i = 0;
        if (customer.getAddressbookList() == null || customer.getAddressbookList().getAddressbook() == null
                || customer.getAddressbookList().getAddressbook()[i] == null) {
            return null;
        }
        return customer.getAddressbookList().getAddressbook()[i].getLabel();
    }
}
