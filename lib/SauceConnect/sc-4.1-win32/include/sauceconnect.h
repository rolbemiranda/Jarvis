#ifndef _SAUCECONNECT_H
#define _SAUCECONNECT_H

#include <stddef.h>

/*
 * The definition of this structure is hidden from the API to be able to
 * provide ABI compatibility if our Sauce Connect implementation changes. Use
 * sc_new() and sc_free() to create/free it.
 */
struct sc_ctx;

/*
 * Create/free a new Sauce Connect context. This can be called multiple times
 * to create independent instances, to e.g. use multiple accounts.
 */
struct sc_ctx *sc_new(void);
void sc_free(struct sc_ctx *ctx);

/*
 * Setter/getter for configuration parameters. vlen is the size of value. Do
 * this before calling sc_init().
 */
#define SC_PARAM_IS_SERVER 0x01     /* int */
#define SC_PARAM_KGP_HOST 0x02      /* char * */
#define SC_PARAM_KGP_PORT 0x03      /* int */
#define SC_PARAM_EXT_HOST 0x04      /* char * */
#define SC_PARAM_EXT_PORT 0x05      /* int */
#define SC_PARAM_LOGFILE 0x06       /* char * */
#define SC_PARAM_LOGLEVEL 0x07      /* int */
#define SC_PARAM_MAX_LOGSIZE 0x08   /* int */
#define SC_PARAM_CERTFILE 0x09      /* char * */
#define SC_PARAM_KEYFILE 0x0a       /* char * */
#define SC_PARAM_LOCAL_PORT 0x0b    /* int */
#define SC_PARAM_USER 0x0c          /* char * */
#define SC_PARAM_API_KEY 0x0d       /* char * */
#define SC_PARAM_PROXY 0x0e         /* char * */
#define SC_PARAM_PROXY_USERPWD 0x0f /* char * */
#define SC_PARAM_RECONNECT 0x10     /* int */
int sc_get(struct sc_ctx *ctx, int param, void *value, size_t vlen);
int sc_set(struct sc_ctx *ctx, int param, void *value);

/*
 * Once we have set up our ctx, do some initialization. This makes sure
 * everything is ready to start a new connection. Do this only after
 * parameters are set via sc_set().
 */
int sc_init(struct sc_ctx *ctx);

/*
 * Run the main event loop: connect to the peer, and start forwarding. Only
 * returns if sc_stop() is called from another thread, or a fatal error
 * happens Sauce Connect can't recover from.
 */
int sc_run(struct sc_ctx *ctx);

/*
 * Stop the Sauce Connect main event loop. This can be used from e.g. another
 * thread to stop SC in a clean way.
 */
int sc_stop(struct sc_ctx *ctx);

/*
 * Get the current status of Sauce Connect.
 */
#define SC_STATUS_RUNNING 0x01
#define SC_STATUS_EXITING 0x02
int sc_status(struct sc_ctx *ctx);

/*
 * Get information on Sauce Connect internals.
 */
#define SC_INFO_KGP_IS_CONNECTED 0x01
#define SC_INFO_KGP_LAST_STATUS_CHANGE 0x02
int sc_get_info(struct sc_ctx *ctx, int what, int *info);
#endif
