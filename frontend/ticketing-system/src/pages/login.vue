<script setup>
import authV1BottomShape from '@images/svg/bottom-illustration.svg?raw'
import authV1TopShape from '@images/svg/top-illustration.svg?raw'
import { VNodeRenderer } from '@layouts/components/VNodeRenderer'
import { themeConfig } from '@themeConfig'
import { requiredValidator } from '@validators';
import { reactive } from 'vue';
import axios from "@axios";
import { useAppAbility } from '@/plugins/casl/useAppAbility';

const customerForm = reactive({
    username: '',
    password: '',
})
const managerForm = reactive({
    username: '',
    password: '',
})
const ticketOfficerForm = reactive({
    username: '',
    password: '',
})

const customerRefForm = ref();
const managerRefForm = ref();
const ticketOfficerRefForm = ref();

const isPasswordVisible = ref(false)
const isLoading = ref(false);
const showError = ref(false);
const errorMessage = ref('');
const ability = useAppAbility()

//Tabs
const currentTab = ref(0);

const login = async () => {
    const loginCustomerURL = "users/loginCustomer";
    const loginManagerURL = "users/loginManager";
    const loginTicketOfficerURL = "users/loginTicketOfficer";

    isLoading.value = true;
    showError.value = false;

    let data = {};
    let url = "";

    console.log("tab: ", currentTab.value);
    if (currentTab.value == 0) {
        url = loginCustomerURL;
        data = {
            userName: customerForm.username,
            password: customerForm.password,
        }
        const valid = await customerRefForm.value.validate()
        if (valid.valid == false) {
            isLoading.value = false;
            return;
        }
    } else if (currentTab.value == 1) {
        url = loginManagerURL;
        data = {
            userName: managerForm.username,
            password: managerForm.password,
        }
        const valid = await managerRefForm.value.validate()
        if (valid.valid == false) {
            isLoading.value = false;
            return;
        }
    } else if (currentTab.value == 2) {
        url = loginTicketOfficerURL;
        data = {
            userName: ticketOfficerForm.username,
            password: ticketOfficerForm.password,
        }
        const valid = await ticketOfficerRefForm.value.validate()
        if (valid.valid == false) {
            isLoading.value = false;
            return;
        }
    };

    console.log("Data: ", data);
    console.log("URL: ", url);

    axios.post(url, data)
        .then(response => {
            console.log("Response: ", response.data);
            localStorage.setItem("UserType", response.data.userType);
            localStorage.setItem("UserId", response.data.userId);
            localStorage.setItem("UserName", response.data.userName);

            const userAbilities = [
                {
                    action: "manage",
                    subject: response.data.userType
                }
            ];

            localStorage.setItem('userAbilities', JSON.stringify(userAbilities));
            ability.update(userAbilities);

        })
        .catch(error => {
            console.log(error.message);
            showError.value = true;
            if (error.response) {
                console.log("Error: ", error.response.data);
                errorMessage.value = error.response.data;
            } else {
                errorMessage.value = error.message;
            }
        });
    isLoading.value = false;
}

</script>

<template>
    <div class="auth-wrapper d-flex align-center justify-center pa-4">
        <div class="position-relative">
            <!-- 👉 Top shape -->
            <VNodeRenderer :nodes="h('div', { innerHTML: authV1TopShape })"
                class="text-primary auth-v1-top-shape d-none d-sm-block" />

            <!-- 👉 Bottom shape -->
            <VNodeRenderer :nodes="h('div', { innerHTML: authV1BottomShape })"
                class="auth-v1-bottom-shape d-none d-sm-block text-primary" />

            <VCard class="auth-card px-2" width="450">
                <VCardItem class="justify-center">
                    <template #prepend>
                        <div class="d-flex">
                            <VNodeRenderer :nodes="themeConfig.app.logo" />
                        </div>
                    </template>

                    <VCardTitle class="auth-title">
                        {{ themeConfig.app.title }}
                    </VCardTitle>
                </VCardItem>

                <VTabs grow v-model="currentTab">
                    <VTab>Customer</VTab>
                    <VTab>Manager</VTab>
                    <VTab>Ticket Officer</VTab>
                </VTabs>

                <!-- Customer -->
                <VWindow v-model="currentTab">
                    <VWindowItem>
                        <VCardText>
                            <h6 class="text-h5 text-center mb-1">
                                Customer Portal
                            </h6>
                        </VCardText>
                        <VCardText>
                            <VForm @submit.prevent ref="customerRefForm">
                                <VRow>

                                    <VCol cols="12">
                                        <VTextField v-model="customerForm.username" autofocus label="Username"
                                            :rules="[requiredValidator]" />
                                    </VCol>

                                    <VCol cols="12">
                                        <VTextField v-model="customerForm.password" label="Password"
                                            :type="isPasswordVisible ? 'text' : 'password'"
                                            :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                                            @click:append-inner="isPasswordVisible = !isPasswordVisible"
                                            :rules="[requiredValidator]" />

                                        <VAlert v-if="showError" variant="tonal" color="error" class="text-center mt-2">
                                            {{ errorMessage }}
                                        </VAlert>
                                        <!-- login button -->
                                        <VBtn class="mt-4" block type="submit" @click="login()">
                                            Login
                                        </VBtn>
                                    </VCol>

                                    <!-- create account -->
                                    <VCol cols="12" class="text-center text-base d-flex justify-center flex-wrap">
                                        <span>New on our platform?</span>
                                        <RouterLink class="text-primary ms-2" :to="{ name: 'register' }">
                                            Create an account
                                        </RouterLink>
                                    </VCol>

                                </VRow>
                            </VForm>
                        </VCardText>
                    </VWindowItem>

                    <!-- Manager -->
                    <VWindowItem>
                        <VCardText>
                            <h6 class="text-h5 text-center mb-1">
                                Manager Portal
                            </h6>
                        </VCardText>
                        <VCardText>
                            <VForm @submit.prevent ref="managerRefForm">
                                <VRow>

                                    <VCol cols="12">
                                        <VTextField v-model="managerForm.username" autofocus label="Username"
                                            :rules="[requiredValidator]" />
                                    </VCol>

                                    <VCol cols="12">
                                        <VTextField v-model="managerForm.password" label="Password"
                                            :type="isPasswordVisible ? 'text' : 'password'"
                                            :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                                            @click:append-inner="isPasswordVisible = !isPasswordVisible"
                                            :rules="[requiredValidator]" />

                                        <VAlert v-if="showError" variant="tonal" color="error" class="text-center mt-2">
                                            {{ errorMessage }}
                                        </VAlert>
                                        <!-- login button -->
                                        <VBtn class="mt-4" block type="submit" @click="login()">
                                            Login
                                        </VBtn>
                                    </VCol>

                                    <!-- create account -->
                                    <VCol cols="12" class="text-center text-base d-flex justify-center flex-wrap">
                                        <span>Ask a developer for an account</span>
                                    </VCol>

                                </VRow>
                            </VForm>
                        </VCardText>
                    </VWindowItem>

                    <!-- Ticket Officer -->
                    <VWindowItem>
                        <VCardText>
                            <h6 class="text-h5 text-center mb-1">
                                Ticket Officer Portal
                            </h6>
                        </VCardText>
                        <VCardText>
                            <VForm @submit.prevent ref="ticketOfficerRefForm">
                                <VRow>

                                    <VCol cols="12">
                                        <VTextField v-model="ticketOfficerForm.username" autofocus label="Username"
                                            :rules="[requiredValidator]" />
                                    </VCol>

                                    <VCol cols="12">
                                        <VTextField v-model="ticketOfficerForm.password" label="Password"
                                            :type="isPasswordVisible ? 'text' : 'password'"
                                            :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                                            @click:append-inner="isPasswordVisible = !isPasswordVisible"
                                            :rules="[requiredValidator]" />

                                        <VAlert v-if="showError" variant="tonal" color="error" class="text-center mt-2">
                                            {{ errorMessage }}
                                        </VAlert>
                                        <!-- login button -->
                                        <VBtn class="mt-4" block type="submit" @click="login()">
                                            Login
                                        </VBtn>
                                    </VCol>

                                    <!-- create account -->
                                    <VCol cols="12" class="text-center text-base d-flex justify-center flex-wrap">
                                        <span>Ask your event manager for an account</span>
                                    </VCol>

                                </VRow>
                            </VForm>
                        </VCardText>
                    </VWindowItem>
                </VWindow>
            </VCard>
        </div>
    </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>

<route lang="yaml">
    meta:
      layout: blank
</route>