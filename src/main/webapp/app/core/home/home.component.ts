import Component from 'vue-class-component';
import { Inject, Vue, Watch } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import { SplitFactory } from '@splitsoftware/splitio';
import { IClient } from '@splitsoftware/splitio/types/splitio';
import axios from 'axios';

const SPLIT_AUTH_KEY = '<yourSplitAuthKey>';

@Component
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  private splitClient: IClient = null;

  // our list of drinks
  private drinks = [];

  // holds the drink that is the current order
  private currentOrder = null;

  // cream or no cream?
  private withCream = false;

  // the current Split.io treatment
  private treatment = null;

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  async getTreatment() {
    // create a configured SplitFactory
    const splitFactory = SplitFactory({
      core: {
        authorizationKey: SPLIT_AUTH_KEY, // your Split.io auth key
        key: this.username, // identifier for this treatment (username in this case)
        trafficType: 'user',
      },
      startup: {
        readyTimeout: 1.5, // 1.5 sec
      },
    });

    // create the split client (NOT READY TO USE YET)
    this.splitClient = splitFactory.client();

    // block untli the client is ready
    this.splitClient.on(
      this.splitClient.Event.SDK_READY,
      function () {
        // client is ready, get the treatment
        this.treatment = this.splitClient.getTreatment('drink-types');
      }.bind(this)
    );
  }

  // triggered when username changes to update list
  // of drinks and Split.io treatment
  @Watch('username')
  async usernameChanged(newVal: string, oldVal: String) {
    // get treatment from split.io
    await this.getTreatment();

    // call the REST service to load drinks
    await this.loadDrinks();

    // clear the current order
    this.currentOrder = null;
  }

  async loadDrinks() {
    const response = await axios.get('http://localhost:8080/api/coffee-bot/list-drinks');
    console.log(response);
    if (response && response.status === 200) {
      this.drinks = response.data;
    } else {
      this.drinks = [];
    }
  }

  async mounted() {
    await this.getTreatment();
    await this.loadDrinks();
  }

  beforeDestroy() {
    this.splitClient.destroy();
  }
}
