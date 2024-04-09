<script setup>
import authV1BottomShape from '@images/svg/bottom-illustration.svg?raw'
import authV1TopShape from '@images/svg/top-illustration.svg?raw'
import { VNodeRenderer } from '@layouts/components/VNodeRenderer'
import { themeConfig } from '@themeConfig'
import { requiredValidator } from '@validators';
import { reactive } from 'vue';
import axios from "@axios";
import { useAppAbility } from '@/plugins/casl/useAppAbility';

const form = reactive({
    username: '',
    password: '',
})

const isPasswordVisible = ref(false)
const refForm = ref();
const isLoading = ref(false);
const showError = ref(false);
const errorMessage = ref('');
const ability = useAppAbility()

const login = async () => {
    const loginCustomerURL = "users/loginCustomer";

    isLoading.value = true;
    showError.value = false;
    const valid = await refForm.value.validate()
    if (valid.valid == false) {
        isLoading.value = false;
        return;
    }

    let data = {
        userName: form.username,
        password: form.password
    }

    console.log("Data: ", data);

    axios.post(loginCustomerURL, data)
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

            <VCard class="auth-card px-2" max-width="450">
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

                <VCardText>
                    <h6 class="text-h5 text-center mb-1">
                        Customer Portal
                    </h6>
                </VCardText>
                <VCardText>
                    <VForm @submit.prevent ref="refForm">
                        <VRow>

                            <VCol cols="12">
                                <VTextField v-model="form.username" autofocus label="Username"
                                    :rules="[requiredValidator]" />
                            </VCol>

                            <VCol cols="12">
                                <VTextField v-model="form.password" label="Password"
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