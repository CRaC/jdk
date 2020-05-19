// Copyright 2017-2020 Azul Systems, Inc.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package javax.crac;

/**
 * {@code Resource} is an abstract entity that should be notified when
 * Java Runtime performs checkpoint or restore (see {@code Core} for definition).
 * Usually {@code Resource} implemented by an object corresponding to some
 * external resource (i.e. of operating system), which is not under full control of the Java Runtime.
 * The {@code Resource}'s implementation task is to align external resource state with a state of the Java object, that is,
 * to provide shutdown procedure for checkpoint and re-initialization for restore operations.
 *
 * {@code Resource} must be registered in a {@code Context} to be notified.
 * For example, {@code Core} provides a global context that dispatches notification
 * to all required {@code Resource}'s and {@code Context}'es.
 *
 */
public interface Resource {

    /**
     * Called in transition to checkpoint.
     *
     * @throws Exception if external resource cannot be shutdown.
     */
    void beforeCheckpoint() throws Exception;

    /**
     * Called in transition from checkpoint to operating state.
     *
     * @throws Exception if external resource cannot be re-initialized.
     */
    void afterRestore() throws Exception;
}
